package com.OrderManagement.Order.gateway.database.jpa;

import com.OrderManagement.Order.gateway.IProductGateway;
import com.OrderManagement.Order.gateway.database.jpa.repository.ProductRepository;

public class ProductJpaGateway implements IProductGateway {

    private final ProductRepository productRepository;

    public ProductJpaGateway(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

}
