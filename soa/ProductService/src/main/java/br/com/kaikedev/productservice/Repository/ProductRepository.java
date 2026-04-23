package br.com.kaikedev.productservice.Repository;

import br.com.kaikedev.productservice.Entity.ProductEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public class ProductRepository {

    private JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final Logger log = LoggerFactory.getLogger(ProductRepository.class);


//    public void Initialize()
//    {
//        String sql = """
//
//                create table if not exists product(
//                	id SERIAL primary key,
//                	name VARCHAR(255) not null,
//                	description VARCHAR(255),
//                	price DECIMAL(10,2) not null,
//                	created_at TIMESTAMP,
//                	updated_at TIMESTAMP,
//                	owner_id INT,
//                	quantity INT not null
//                );
//
//                create table if not exists product_image(
//                	id SERIAL primary key,
//                	product_id INT not null,
//                	image text,
//                	foreign key (product_id) references product(id) on delete cascade
//                );
//
//                """;
//
//
//        jdbcTemplate.execute(sql);
//
//
//    }


    public Collection<ProductEntity> getProducts() {
        String sql = "SELECT id, name, description, price, created_at, updated_at, owner_id ,quantity FROM product";
        try {
            log.info("Executing query: " + sql);
            return jdbcTemplate.query(sql, (rs, rowNum) -> new ProductEntity(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime(),
                    rs.getInt("owner_id"),
                    rs.getInt("quantity")
            ));
        } catch (Exception exception) {
            log.error("Error in getProducts: {}", exception.getMessage());
            return null;
        }
    }

    public ProductEntity getProduct(Integer id) {
        String sql = "SELECT id, name, description, price, created_at, updated_at, owner_id, quantity FROM product WHERE id = ?";
        try {
            log.info("Executing query: " + sql + " with id: " + id);
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new ProductEntity(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime(),
                    rs.getInt("owner_id"),
                    rs.getInt("quantity")
            ), id);
        } catch (Exception exception) {
            log.error("Error in getProduct: {}", exception.getMessage());
            return null;
        }
    }

    public boolean insertProduct(ProductEntity product) {
        String sql = "INSERT INTO product (name, description, price, created_at, updated_at, owner_id, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        LocalDateTime now = LocalDateTime.now();

        product.setCreatedAt(now);
        product.setUpdatedAt(now);

        try {
            log.info("Executing query: {} with {}", sql, product.toString());
            jdbcTemplate.update(sql,
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    Timestamp.valueOf(product.getCreatedAt()),
                    Timestamp.valueOf(product.getUpdatedAt()),
                    product.getOwnerId(),
                    product.getQuantity()
            );
            return true;
        } catch (Exception exception) {
            log.error("Error in insertProduct: {}", exception.getMessage());
            return false;
        }
    }

    public boolean updateProduct(ProductEntity product) {
        String sql = "UPDATE product SET name = ?, description = ?, price = ?, updatedAt = ?, quantity = ? WHERE id = ?";
        product.setUpdatedAt(LocalDateTime.now());

        try {
            log.info("Executing query: " + sql);
            jdbcTemplate.update(sql,
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getUpdatedAt(),
                    product.getQuantity(),
                    product.getId()
            );
            return true;
        } catch (Exception exception) {
            log.error("Error in updateProduct: {}", exception.getMessage());
            return false;
        }
    }

    public ProductEntity uptadeStock(ProductEntity productEntity) {
        String sql = "UPDATE product SET quantity = ?, updated_at = ? WHERE id = ?";

        try {
            log.info("Executing query: {} with {}", sql, productEntity.toString());
            jdbcTemplate.update(sql, productEntity.getQuantity(),Timestamp.valueOf(LocalDateTime.now()),productEntity.getId());
        } catch (Exception exception) {
            log.error("Error in uptadeStock: {}", exception.getMessage());
        }
        return productEntity;
    }

    public boolean deleteProduct(Integer id) {
        String sql = "DELETE FROM product WHERE id = ?";
        try {
            log.info("Executing query: " + sql);
            jdbcTemplate.update(sql, id);
            return true;
        } catch (Exception exception) {
            log.error("Error in deleteProduct: {}", exception.getMessage());
            return false;
        }
    }
}
