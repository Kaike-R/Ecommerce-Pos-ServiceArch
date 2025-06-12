package br.com.kaikedev.orderservice.RestCall;


import br.com.kaikedev.orderservice.Entity.Dto.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Objects;

@Component
public class ProductClient {

    private static final Logger log = LoggerFactory.getLogger(ProductClient.class);

    private RestClient restClient;

    //@Value("${intern.endpoint.ProductService}")
    //private String productEndpoint;

    public ProductClient(@Value("${internal.endpoint.GatewayService}") String urlBase) {
        this.restClient = RestClient.builder()
                .baseUrl(urlBase)
                .build();
    }


    public Boolean CheckStock(Integer productId, Integer quantity)
    {
        log.info("Checking stock for product id {} and quantity {}", productId, quantity);

        var ProductDto = getProduct(productId);

        if (ProductDto == null) {
            return false;
        }
        if (ProductDto.getQuantity() < quantity) {
            return false;
        }
        return true;
    }

    public ProductDto getProduct(Integer productId) {
        Objects.requireNonNull(productId, "Product ID cannot be null");

        try {
            log.info("Retrieving product id {}", productId);

            ProductDto productResponse = restClient.get()
                    .uri("/v1/product-service/api/{id}", productId)
                    .retrieve()
                    .onStatus(status -> status.isError(), (request, response) -> {
                        throw new RuntimeException("Failed to get product: " + response.getStatusCode());
                    })
                    .body(ProductDto.class);

            if (productResponse == null) {
                throw new RuntimeException("Null response from product service");
            }

            log.info("Retrieved product: {}", productResponse);
            productResponse.setId(productId); // Por que setar o ID se veio da API?
            return productResponse;

        } catch (RestClientException e) {
            log.error("Error retrieving product {}: {}", productId, e.getMessage());
            throw new RuntimeException("Could not retrieve product: " + productId, e);
        }
    }

    public ProductDto updateStock(Integer id, Integer quantity) {

        log.info("Updating stock for product id {} and quantity {}", id, quantity);
        ProductDto productResponse = null;
        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setQuantity(quantity);
        try {
            productResponse = restClient.put().uri("/v1/product-service/api/updateStocks").body(productDto).retrieve().toEntity(ProductDto.class).getBody();
        } catch (RestClientException e) {
            log.error(e.getMessage());
        }

        return productResponse;
    }
}
