package com.OrderManagement.Order.gateway.adapters.MSProduct.fallback;

import com.OrderManagement.Order.gateway.adapters.MSProduct.ProductService;
import com.OrderManagement.Order.gateway.adapters.MSProduct.dto.ProductServiceDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceFallback implements ProductService {

    @Override
    public ResponseEntity<ProductServiceDto> getProductBySKU(Long SKU) {
        ProductServiceDto productServiceDto = new ProductServiceDto(SKU,
                null,
                null,
                null,
                null,
                -1);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(productServiceDto);
    }
}