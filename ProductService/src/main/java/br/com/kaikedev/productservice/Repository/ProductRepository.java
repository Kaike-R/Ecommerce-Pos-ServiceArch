package br.com.kaikedev.productservice.Repository;


import br.com.kaikedev.productservice.Entity.ProductEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;


@Repository
public class ProductRepository {

    private JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final Logger log = LoggerFactory.getLogger(ProductRepository.class);

    public Collection<ProductEntity> getProducts() {
        String sql = "select * from product";
        Collection<ProductEntity> products = null;

        try {
            log.info("Executing query: " + sql);
            products = jdbcTemplate.query(sql, (rs, rowNum) -> new ProductEntity(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price")
            ));
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }


        return products;

    }

    public ProductEntity getProduct(int id) {
        String sql = "select * from product where id = ?";
        ProductEntity product = null;

        try {
            log.info("Executing query: " + sql);
            product = jdbcTemplate.queryForObject(sql, (rs, rownow) -> new ProductEntity(
               rs.getInt("id"),
               rs.getString("name"),
               rs.getString("description"),
               rs.getDouble("price")
            ), id);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

        return product;
    }

    public boolean insertProduct(ProductEntity product) {
        String sql = "insert into product(id, name, description, price) values(?,?,?,?)";
        Integer nextId = jdbcTemplate.queryForObject("SELECT COALESCE(MAX(id), 0) + 1 FROM product", Integer.class);
        try {
            log.info("Executing query: " + sql);
            jdbcTemplate.update(sql, nextId, product.getName(), product.getDescription(), product.getPrice());
            return true;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return false;
        }
    }

    public boolean updateProduct(ProductEntity product) {
        String sql = "update product set name = ?, description = ?, price = ? where id = ?";
        Integer nextId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM product", Integer.class);
        try {
            log.info("Executing query: " + sql);
            jdbcTemplate.update(sql, product.getId(), product.getName(), product.getDescription(), product.getPrice());
            return true;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return false;
        }
    }

    public boolean deleteProduct(Integer id) {
        String sql = "delete from product where id = ?";
        try {
            log.info("Executing query: " + sql);
            jdbcTemplate.update(sql, id);
            return true;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return false;
        }

    }

}
