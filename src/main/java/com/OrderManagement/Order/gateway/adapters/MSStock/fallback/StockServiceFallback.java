package com.OrderManagement.Order.gateway.adapters.MSStock.fallback;

import com.OrderManagement.Order.gateway.adapters.MSStock.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class StockServiceFallback{

    public ResponseEntity<StockDto> removeQuantityInventory(String sku, int quantity) {
        return ResponseEntity.status(503).build();
    }
}
