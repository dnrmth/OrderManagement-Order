//package com.OrderManagement.Order.usecase;
//
//import com.OrderManagement.Order.controller.dto.PaymentDto;
//import com.OrderManagement.Order.domain.enums.StatusOrder;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//
//
//public class CreateOrderUseCaseTest {
//
//    CreateOrderUseCase createOrderUseCase;
//    @Test
//    void createOrder(){
//
//        assertDoesNotThrow(() -> {
//            createOrderUseCase.createOrder(List.of(),
//                    1L,
//                    new PaymentDto(
//                      "1234-5678-9012-1243",  "Credit",  "cardHolderName",
//                            "cardExpiryDate", 123
//            ),
//                    StatusOrder.ABERTO
//            );
//        });
//    }
//}
