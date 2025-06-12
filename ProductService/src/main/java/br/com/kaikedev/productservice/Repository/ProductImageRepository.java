package br.com.kaikedev.productservice.Repository;

import br.com.kaikedev.productservice.Entity.ProductEntity;
import br.com.kaikedev.productservice.Entity.ProductImageEntity;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
public class ProductImageRepository {

    private static final Logger log = LoggerFactory.getLogger(ProductImageRepository.class);
    JdbcTemplate jdbcTemplate;

    public ProductImageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductImageEntity> findImageByProductId(Integer productId) {

        String sql = "select id, product_id, image from product_image where product_id=?";

        try {
            List<ProductImageEntity> images = jdbcTemplate.query(sql, (rs, rownow) -> new ProductImageEntity(
                    rs.getInt("id"),
                    rs.getInt("product_id"),
                    rs.getString("image")
            ), productId);
            log.info(images.toString());
            return images;
        }
        catch (DataAccessException e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }

    }

    public List<ProductImageEntity> findAll() {
        String sql = "select id, product_id, image from product_image";
        return jdbcTemplate.query(sql, (rs, rownow) -> new ProductImageEntity(
                rs.getInt("id"),
                rs.getInt("product_id"),
                rs.getString("image")
        ));
    }

    public Integer save(Integer productId, String image) {
        String sql = "insert into product_image(product_id,image) values(?,?)";
        Integer x = jdbcTemplate.update(sql, productId, image);
        return x;
    }

    public Integer delete(Integer id) {
        String sql = "delete from product_image where id=?";
        Integer x = jdbcTemplate.update(sql, id);
        return x;
    }


}
