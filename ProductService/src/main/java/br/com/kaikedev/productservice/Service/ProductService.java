package br.com.kaikedev.productservice.Service;


import br.com.kaikedev.productservice.Entity.Dto.ProductDto;
import br.com.kaikedev.productservice.Entity.Dto.ProductResponse;
import br.com.kaikedev.productservice.Entity.ProductEntity;
import br.com.kaikedev.productservice.Entity.ProductImageEntity;
import br.com.kaikedev.productservice.Repository.ProductImageRepository;
import br.com.kaikedev.productservice.Repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private ProductRepository productRepository;

    private ProductImageRepository productImageRepository;

    public ProductService(ProductRepository productRepository, ProductImageRepository productImageRepository) {

        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
    }
    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> productResponses = new ArrayList<>();
        Collection<ProductEntity> productEntities = productRepository.getProducts();
        log.info("Total products: " + productEntities.size());
        for (ProductEntity productEntity : productEntities) {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setName(productEntity.getName());
            productResponse.setDescription(productEntity.getDescription());
            productResponse.setPrice(productEntity.getPrice());
            productResponse.setQuantity(productEntity.getQuantity());
            productResponses.add(productResponse);
        }
        return productResponses;
    }


    public ProductDto getProductById(int id) {
        ProductEntity productEntity = productRepository.getProduct(id);
        List<ProductImageEntity> productImageEntity = productImageRepository.findImageByProductId(productEntity.getId());
        List<String> temp = List.of();


        for (ProductImageEntity imageEntity : productImageEntity) {
           temp.add(imageEntity.getImage());
        }


        ProductDto productDto = new ProductDto();

        productDto.setName(productEntity.getName());
        productDto.setDescription(productEntity.getDescription());
        productDto.setImages(temp);
        return productDto;
    }

    public Boolean createProduct(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setQuantity(5);
        productEntity.setOwnerId(1);
        return productRepository.insertProduct(productEntity);
    }

    public ProductEntity uptadeStock(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setQuantity(productDto.getQuantity());
        return productRepository.uptadeStock(productEntity);
    }

    public ProductDto updateProduct(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        return productDto;
    }

    public void deleteProduct(int id) {
        productRepository.deleteProduct(id);
    }



    public List<String> findImageByProductId(Integer productId) {
        List<ProductImageEntity> productImageEntities = productImageRepository.findImageByProductId(productId);
        List<String> temp = List.of();

        productImageEntities.forEach(productImageEntity -> {
            temp.add(productImageEntity.getImage());
        });

        return temp;

    }




}
