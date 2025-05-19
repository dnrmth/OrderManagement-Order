package com.OrderManagement.Order.gateway.adapters.MSProduct;

import com.OrderManagement.Order.gateway.adapters.MSProduct.dto.ProductServiceDto;
import com.OrderManagement.Order.gateway.adapters.MSProduct.fallback.ProductServiceFallback;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceFallbackTest {
    @Test
    void getProductBySKU_ShouldReturnServiceUnavailable() {
        ProductServiceFallback productServiceFallback = new ProductServiceFallback();

        Long sku = 12345L;

        ResponseEntity<ProductServiceDto> response = productServiceFallback.getProductBySKU(sku);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNull(response.getBody().sku());
        assertEquals(-1, response.getBody().quantity());
        assertNull(response.getBody().name());
        assertNull(response.getBody().description());
        assertNull(response.getBody().price());
    }
}
