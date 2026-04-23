package br.com.kaikedev.productservice.Repository;


import br.com.kaikedev.productservice.Entity.ProductEntity;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;


import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-tests.properties")
public class ProductRepositoryTests {


    private static final Logger log = LoggerFactory.getLogger(ProductRepositoryTests.class);


    private Faker faker = new Faker();

    private Integer number;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void resetDatabase(@Autowired JdbcTemplate jdbcTemplate) {
        ProductEntity productEntity = new ProductEntity();

        productEntity.setPrice(faker.number().randomDouble(2,1, 10000));
        productEntity.setName(faker.name().fullName());
        productEntity.setDescription(faker.lorem().word());



        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");

        Collection<String> tables = jdbcTemplate.queryForList(
                "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'",
                String.class
        );

        for (String table : tables) {
            jdbcTemplate.execute("TRUNCATE TABLE " + table);
        }

        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
        //jdbcTemplate.execute(data);

        productRepository.insertProduct(productEntity);

        number = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM PRODUCT ", Integer.class);
        log.info("Number of products in database: {}", number);
    }

    @Test
    void testTabelaExiste() {
        jdbcTemplate.execute("SELECT 1 FROM product");
    }

    @Test
    public void getProducts() {

        Collection<ProductEntity> x = productRepository.getProducts();

        log.info(x.stream().findFirst().get().toString());

        assertEquals(number, x.size());


    }

    @Test
    void insertProduct() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(faker.name().fullName());
        productEntity.setDescription(faker.lorem().word());
        productEntity.setPrice(faker.number().randomDouble(2,1, 10000));

        assertEquals(true, productRepository.insertProduct(productEntity));
    }

    @Test
    void getProduct() {
        ProductEntity productEntity = productRepository.getProduct(1);

        log.info(productEntity.toString());

        assertEquals(number, productEntity.getId());
    }

    @Test
    void updateProduct() {

    }
}
