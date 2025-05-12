package br.com.kaikedev.productservice.Repository;

import br.com.kaikedev.productservice.Entity.ProductEntity;
import br.com.kaikedev.productservice.Entity.ProductImageEntity;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class ProductImageRepository {

    JdbcTemplate jdbcTemplate;

    public ProductImageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductImageEntity> findImageByProductId(Integer productId) {

        String sql = "select * from product_image where product_id=?";


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
