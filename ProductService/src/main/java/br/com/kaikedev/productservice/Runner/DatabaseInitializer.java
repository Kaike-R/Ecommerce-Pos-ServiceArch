package br.com.kaikedev.productservice.Runner;

import br.com.kaikedev.productservice.Entity.ProductEntity;
import br.com.kaikedev.productservice.Entity.ProductImageEntity;
import br.com.kaikedev.productservice.Repository.ProductImageRepository;
import br.com.kaikedev.productservice.Repository.ProductRepository;
import br.com.kaikedev.productservice.Service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {


    private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);
    private ProductRepository productRepository;
    private ProductImageRepository productImageRepository;

    public DatabaseInitializer(ProductRepository productRepository, ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
    }

    private void productImageInitializer()
    {
        var initialization = productImageRepository.findAll().size();

        if (initialization < 10000000) {
            Faker faker = new Faker();


            for (int i = initialization; i < 10000000; i++) {
                ProductImageEntity p = new ProductImageEntity();
                p.setProductId(faker.number().numberBetween(1,100000));
                p.setImage(faker.company().url());

                productImageRepository.save(p.getProductId(), p.getImage());
            }

            log.info("Image Database initialization complete.");
            log.info("Number of products: {} ", productImageRepository.findAll().size());
        }

    }

    @Override
    public void run(String... args) throws Exception {
        var initialization = productRepository.getProducts().size();

        if (initialization < 100000) {
            Faker faker = new Faker();

            for (int i = initialization; i < 100000; i++) {
                ProductEntity p = new ProductEntity();
                p.setName(faker.commerce().productName());
                p.setQuantity(faker.number().numberBetween(0, 1000));
                p.setDescription(faker.lorem().sentence());
                if (i % 2 == 0) {
                    p.setOwnerId(2001);
                }
                if (p.getName().contains("x")) {
                    p.setOwnerId(9001);
                }
                if (p.getQuantity() % 2 == 0) {
                    p.setOwnerId(1);
                }
                else {
                    p.setOwnerId(4001);
                }
                //p.setPrice(faker.number().randomDouble(1,5,10000));
                p.setPrice(Double.valueOf(faker.commerce().price(1, 300)));
                productRepository.insertProduct(p);

            }

            log.info("Database initialization complete.");
            log.info("Number of products: {} ", productRepository.getProducts().size());

        }

        productImageInitializer();

    }

//    @Override
//    public void run(String... args) throws Exception {
//
//        productRepository.Initialize();
//
//    }
}
