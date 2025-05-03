package com.OrderManagement.Order.gateway.adapters.MSProduct.fallback;

import com.OrderManagement.Order.gateway.adapters.MSProduct.ProductService;
import com.OrderManagement.Order.gateway.adapters.MSProduct.dto.ProductServiceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class ProductServiceFallback implements ProductService {

    @Override
    public ResponseEntity<ProductServiceDto> getProductBySKU(Long SKU) {
        return ResponseEntity.status(503).build();
    }
}
