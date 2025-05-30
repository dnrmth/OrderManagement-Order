package com.OrderManagement.Order.gateway.adapters.MSStock;

import com.OrderManagement.Order.gateway.adapters.MSStock.fallback.StockServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="StockService",
        url = "${stock.service.url}",
        fallback = StockServiceFallback.class)
public interface StockService {

    @PutMapping("/removeQuantityInventory/{sku}/{quantity}")
    ResponseEntity<?> removeQuantityInventory(@PathVariable("sku") String sku, @PathVariable("quantity") int quantity);

    @PutMapping("/addQuantityInventory/{sku}/{quantity}")
    ResponseEntity<?> addQuantityInventory(@PathVariable("sku") String sku, @PathVariable("quantity") int quantity);
}
