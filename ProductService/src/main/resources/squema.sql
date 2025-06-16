create table if not exists productdatabase.product(
	id SERIAL primary key,
	name VARCHAR(255) not null,
	description VARCHAR(255),
	price DECIMAL(10,2) not null,
	created_at TIMESTAMP,
	updated_at TIMESTAMP,
	owner_id INT,
	quantity INT not null
);

create table if not exists productdatabase.product_image(
	id SERIAL primary key,
	product_id INT not null,
	image text,
	foreign key (product_id) references product(id) on delete cascade
);

select * from product;