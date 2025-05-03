package com.OrderManagement.Order.usecase;

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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final IOderGateway orderGateway;
    private final ClientService clientService;
    private final PaymentService paymentService;
    private final ProductService productService;
    private final StockService stockService;

    public OrderDto createOrder(List<ProductVOrderDto> productsSKU, Long clientId, PaymentDto payment, StatusOrder statusOrder) {
        if (productsSKU == null || productsSKU.isEmpty()) {
            throw new IllegalArgumentException("Product list cannot be null or empty");
        }

        // Checks if the client is active through the client service
        if(!clientService.confirmClientIsActive(clientId).getStatusCode().is2xxSuccessful()){
            throw new IllegalArgumentException("Client is not active");
        }

        List<ProductVOrder> productList = productsSKU.stream()
                .map(p -> fetchProduct(p.productId()))
                .toList();

        var checkStockUpdate = updateStock(productList);

        var order = orderGateway.createOrder(new Order(productList, clientId, payment, statusOrder));

        var checkPayment = makePayment(payment, order);

        return new OrderDto(order);
    }

    /**
     * Updates the stock for each product in the order.
     * @param products The list of products to update stock for.
     * @return true if stock update is successful, throws Exception otherwise
     */
    private boolean updateStock(List<ProductVOrder> products) {
        products.forEach(productVOrder -> {
            var stock = stockService.updateStock(productVOrder.getSKU(), productVOrder.getQuantity());

            if (!stock.getStatusCode().is2xxSuccessful()){
                throw new IllegalArgumentException(stock.getStatusCode() + " Stock update failed");
            }
        });
        return true;
    }

    /**
     * Fetches the product information from the product service using the SKU.
     * @param productSKU The SKU of the product to fetch.
     * @return The ProductVOrder object containing product information.
     */
    private ProductVOrder fetchProduct(Long productSKU) {
        var productServiceDto = productService.getProductBySKU(productSKU);

        if(!productServiceDto.getStatusCode().is2xxSuccessful()){
            throw new IllegalArgumentException(productServiceDto.getStatusCode() + " is not 2xx successful");
        }
        if(productServiceDto.getBody() == null){
            throw new IllegalArgumentException("Product not found");
        }

        var product = productServiceDto.getBody();

        return new ProductVOrder(product.id(), product.sku(), product.quantity(), product.price());
    }

    /**
     * Sends the payment information to the payment service and checks if the payment was successful.
     * @return true if payment is successful, throws Exception otherwise.
     */
    private boolean makePayment(PaymentDto payment, Order order) {
        CardServiceDTO cardServiceDTO = new CardServiceDTO(payment.cardNumber(), payment.cvv(), payment.cardHolderName(), payment.cardExpiryDate());
        PaymentServiceDto paymentServiceDto = new PaymentServiceDto(order.getId(), cardServiceDTO, order.getTotalPrice(), order.getId());

        var paymentServiceResponse = paymentService.makePayment(paymentServiceDto);

        if(!paymentServiceResponse.getStatusCode().is2xxSuccessful()){
            throw new IllegalArgumentException(paymentServiceResponse.getStatusCode() + " Payment failed");
        }
        if(Objects.requireNonNull(paymentServiceResponse.getBody()).orderId() == 9999L){
            order.setStatusOrder(StatusOrder.FECHADO_SEM_CREDITO);
        }
        return true;
    }
}
