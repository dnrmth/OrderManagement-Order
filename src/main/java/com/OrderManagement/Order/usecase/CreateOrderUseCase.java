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

        if(!clientService.confirmClientIsActive(clientId)){
            throw new IllegalArgumentException("Client is not active");
        }

        List<ProductVOrder> productList = productsSKU.stream()
                .map(p -> fetchProduct(p.productId()))
                .toList();

        updateStock(productList);

        var order = orderGateway.createOrder(new Order(productList, clientId, payment, statusOrder));

        makePayment(payment, order);

        return new OrderDto(order);
    }

    private void updateStock(List<ProductVOrder> products) {
        products.forEach(productVOrder -> {
            var stock = stockService.updateStock(
                    productVOrder.getSKU(), productVOrder.getQuantity());

            if (!stock.getStatusCode().is2xxSuccessful()){
                throw new IllegalArgumentException("Stock update failed");
            }
        });
    }


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

    private void makePayment(PaymentDto payment, Order order) {
        CardServiceDTO cardServiceDTO = new CardServiceDTO(payment.cardNumber(), payment.cvv(), payment.cardHolderName(), payment.cardExpiryDate());
        PaymentServiceDto paymentServiceDto = new PaymentServiceDto(order.getId(), cardServiceDTO, order.getTotalPrice(), order.getId());

        if(!paymentService.makePayment(paymentServiceDto).getStatusCode().is2xxSuccessful()){
            throw new IllegalArgumentException("Payment failed");
        }
    }
}
