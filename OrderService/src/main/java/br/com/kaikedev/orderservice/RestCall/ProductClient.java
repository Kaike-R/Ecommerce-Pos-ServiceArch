package br.com.kaikedev.orderservice.RestCall;


import br.com.kaikedev.orderservice.Entity.Dto.ProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductClient {

    private RestTemplate restTemplate;

    //@Value("${intern.endpoint.ProductService}")
    //private String productEndpoint;

    public ProductClient() {
        this.restTemplate = new RestTemplate();
    }


    public Boolean CheckStock(Integer productId)
    {
        return true;
    }

    public ProductDto getProduct(Integer productId) {
        //return restTemplate.getForObject(productEndpoint, ProductDto.class);
        return new ProductDto(1,"nome",10d,10);
    }

    public void updateStock(Integer id, Integer quantity) {
    }
}
