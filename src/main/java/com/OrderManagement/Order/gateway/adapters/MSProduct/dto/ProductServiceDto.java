package com.OrderManagement.Order.gateway.adapters.MSProduct.dto;

public record ProductServiceDto(Long id, String name, String description, String sku, Double price, Integer quantity) {
}
