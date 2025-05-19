package com.OrderManagement.Order.usecase.create_order;

import com.OrderManagement.Order.controller.dto.OrderDto;
import com.OrderManagement.Order.controller.dto.ProductVOrderDto;
import com.OrderManagement.Order.domain.Order;
import com.OrderManagement.Order.controller.dto.PaymentDto;
import com.OrderManagement.Order.domain.ProductVOrder;
import com.OrderManagement.Order.domain.enums.StatusOrder;
import com.OrderManagement.Order.gateway.IOderGateway;
import com.OrderManagement.Order.gateway.adapters.MSClient.ClientService;
import com.OrderManagement.Order.gateway.adapters.MSPayment.PaymentService;
import com.OrderManagement.Order.gateway.adapters.MSPayment.dto.CardServiceDTO;
import com.OrderManagement.Order.gateway.adapters.MSPayment.dto.PaymentServiceDto;
import com.OrderManagement.Order.gateway.adapters.MSProduct.ProductService;
import com.OrderManagement.Order.gateway.adapters.MSStock.StockService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase implements ICreateOrderUseCase {

    private final IOderGateway orderGateway;
    private final ClientService clientService;
    private final PaymentService paymentService;
    private final ProductService productService;
    private final StockService stockService;
    private final KafkaTemplate<String, OrderDto> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(CreateOrderUseCase.class);

    @KafkaListener(topics = "create-order", groupId = "order-group")
    @Override
    public OrderDto createOrder(OrderDto orderDto) {

        List<ProductVOrderDto> productsSKU = orderDto.products();
        Long clientId = orderDto.clientId();
        PaymentDto payment = orderDto.payment();
        StatusOrder statusOrder = orderDto.statusOrder();

        if (productsSKU == null || productsSKU.isEmpty()) {
            throw new IllegalArgumentException("Product list cannot be null or empty");
        }

        confirmClientIsActive(clientId);

        List<ProductVOrder> productList = productsSKU.stream()
                .map(p -> fetchProduct(p.productId(), p.quantity()) )
                .toList();

        var checkStockUpdate = removeFromStock(productList);

        var order = orderGateway.createOrder(new Order(productList, clientId, payment, statusOrder));

        var checkPayment = makePayment(payment, order);

        updateOrderStatus(checkPayment, checkStockUpdate, order);

        var createdOrderDto = new OrderDto(order);

        sendCreatedOrderToQueue(createdOrderDto);

        return createdOrderDto;
    }

    private void updateOrderStatus(boolean checkPayment, boolean checkStockUpdate, Order order) {
        if (!checkPayment) {
            return;
        }
        if (!checkStockUpdate) {
            order.setStatusOrder(StatusOrder.FECHADO_SEM_ESTOQUE);
            orderGateway.createOrder(order);
            return;
        }
        order.setStatusOrder(StatusOrder.FECHADO_COM_SUCESSO);
        orderGateway.createOrder(order);
    }

    private void sendCreatedOrderToQueue(OrderDto order) {
        try{
            Future<?> future = kafkaTemplate.send("receive-created-order", order);

            future.get();
        }
        catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**pretty self-explanatory*/
    private void confirmClientIsActive(Long clientId) {
        if(!clientService.confirmClientIsActive(clientId).getStatusCode().is2xxSuccessful()){
            throw new IllegalArgumentException("Client is not active");
        }
    }

    /**
     * Updates the stock for each product in the order.
     * @param products The list of products to update stock for.
     * @return true if stock update is successful, throws Exception otherwise
     */
    private boolean removeFromStock(List<ProductVOrder> products) {
        for(ProductVOrder productVOrder : products) {
            var stock = stockService.removeQuantityInventory(productVOrder.getSKU(), productVOrder.getQuantity());

            if (!stock.getStatusCode().is2xxSuccessful()){
                logger.error("Falha ao atualizar o estoque para o produto SKU: {}. Status: {}", productVOrder.getSKU(), stock.getStatusCode());
                return false;
            }
        }
        return true;
    }

    /**
     * Fetches the product information from the product service using the SKU.
     * @param productSKU The SKU of the product to fetch.
     * @return The ProductVOrder object containing product information.
     */
    private ProductVOrder fetchProduct(Long productSKU, int quantity) {
        var productServiceDto = productService.getProductBySKU(productSKU);

        if(!productServiceDto.getStatusCode().is2xxSuccessful()){
            throw new IllegalArgumentException(productServiceDto.getStatusCode() + " is not 2xx successful");
        }
        if(productServiceDto.getBody() == null){
            throw new IllegalArgumentException("Product not found");
        }

        var product = productServiceDto.getBody();

        return new ProductVOrder(product.id(), product.sku(),quantity, product.price());
    }

    /**
     * Sends the payment information to the payment service and checks if the payment was successful.
     * @return true if payment is successful, throws Exception otherwise.
     */
    protected boolean makePayment(PaymentDto payment, Order order) {
        CardServiceDTO cardServiceDTO = new CardServiceDTO(payment.cardNumber(), payment.cvv(), payment.cardHolderName(), payment.cardExpiryDate());
        PaymentServiceDto paymentServiceDto = new PaymentServiceDto(order.getId(), cardServiceDTO, order.getTotalPrice(), order.getId());

        var paymentServiceResponse = paymentService.makePayment(paymentServiceDto);

        if(!paymentServiceResponse.getStatusCode().is2xxSuccessful()){
            order.setStatusOrder(StatusOrder.FECHADO_SEM_CREDITO);

            returnToStock(order.getProducts());

            orderGateway.createOrder(order);
            logger.error("Falha no pagamento. Status: {}", paymentServiceResponse.getStatusCode());
            return false;
        }
        return true;
    }

    private void returnToStock(List<ProductVOrder> products) {

        products.forEach(product -> {
            var stock = stockService.addQuantityInventory(product.getSKU(), product.getQuantity());

            if (!stock.getStatusCode().is2xxSuccessful()){
                throw new IllegalArgumentException(stock.getStatusCode() + " Stock update failed");
            }
        });
    }
}
