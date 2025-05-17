package com.OrderManagement.Order.gateway.adapters.MSStock;

import com.OrderManagement.Order.gateway.adapters.MSStock.fallback.StockDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="StockService",
        url = "${stock.service.url}")
public interface StockService {

    @PutMapping("/removeQuantityInventory/{sku}/{quantity}")
    ResponseEntity<StockDto> removeQuantityInventory(@PathVariable("sku") String sku, @PathVariable("quantity") int quantity);
}
