CREATE TABLE IF NOT EXISTS product(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    price DECIMAL(10,2)
);

CREATE TABLE IF NOT EXISTS product_image(
    id SERIAL PRIMARY KEY,
    product_id INTEGER REFERENCES product(id),
    image TEXT
)