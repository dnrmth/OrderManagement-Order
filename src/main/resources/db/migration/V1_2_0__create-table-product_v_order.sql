CREATE TABLE product_v_order (
    id SERIAL NOT NULL,
    order_id INTEGER NOT NULL,
    sku VARCHAR(255) NOT NULL,
    product_id INTEGER NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES "orders"(id)
);