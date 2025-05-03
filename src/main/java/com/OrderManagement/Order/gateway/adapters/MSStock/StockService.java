package com.OrderManagement.Order.gateway.adapters.MSStock;

import com.OrderManagement.Order.gateway.adapters.MSStock.fallback.StockServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="StockService",
        url = "http://localhost:8085/inventory",
        fallback = StockServiceFallback.class)
public interface StockService {

    @PutMapping("/updateInventory/{sku}/{quantity}")
    ResponseEntity<Void> updateStock(@PathVariable String sku, @PathVariable int quantity);
}
