package br.com.kaikedev.productservice.Runner;

import br.com.kaikedev.productservice.Repository.ProductRepository;
import br.com.kaikedev.productservice.Service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DatabaseInitializer {

    private ProductRepository productRepository;

    public DatabaseInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    @Override
//    public void run(String... args) throws Exception {
//
//        productRepository.Initialize();
//
//    }
}
