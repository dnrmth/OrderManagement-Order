CREATE TABLE "orders" (
    id SERIAL PRIMARY KEY,
    client_id INTEGER NOT NULL,
    order_date TIME NOT NULL,
    status VARCHAR(20) NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL
);