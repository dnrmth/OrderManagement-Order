package com.OrderManagement.Order.gateway.adapters.MSProduct;

import com.OrderManagement.Order.gateway.adapters.MSProduct.dto.ProductServiceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ProductService", url = "http://localhost:8084/product")
public interface ProductService {

    @GetMapping(value = "/{SKU}")
    ResponseEntity<ProductServiceDto> getProductBySKU(@PathVariable Long SKU);
}
