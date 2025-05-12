INSERT INTO product (id, name, description, price, created_at, updated_at, quantity) VALUES
(1, 'Câmera HD', 'Câmera de alta definição para fotos e vídeos.', 599.90, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + 5, 20),
(2, 'Fone Bluetooth', 'Fone de ouvido sem fio com redução de ruído.', 299.90, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + 6, 30);

INSERT INTO product_image (product_id, image) VALUES
(1, 'iVBORw0KGgoAAAANSUhEUgAAAAUA'),
(1, 'dGVzdCBpbWFnZSAy'),
(2, 'Rk9ORV9JTV9CQVNFNjQ='),
(2, 'QU5PVEhFUl9JTV9CQVNFNjQ=');