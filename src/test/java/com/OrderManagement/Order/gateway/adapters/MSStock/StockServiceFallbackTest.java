package com.OrderManagement.Order.gateway.adapters.MSStock;

import com.OrderManagement.Order.gateway.adapters.MSStock.fallback.StockServiceFallback;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockServiceFallbackTest {

    @Test
    void addQuantityInventory(){
        StockServiceFallback stockServiceFallback = new StockServiceFallback();
        var response = stockServiceFallback.addQuantityInventory ("180525-0001", 10);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
    }

    @Test
    void removeQuantityInventory(){
        StockServiceFallback stockServiceFallback = new StockServiceFallback();
        var response = stockServiceFallback.removeQuantityInventory ("180525-0001", 10);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
    }
}
