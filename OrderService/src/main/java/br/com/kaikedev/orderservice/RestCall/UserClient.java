package br.com.kaikedev.orderservice.RestCall;


import br.com.kaikedev.orderservice.Entity.Dto.UserRequest;
import br.com.kaikedev.orderservice.Entity.Dto.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
@Component
public class UserClient {

    private static final Logger log = LoggerFactory.getLogger(UserClient.class);
    private RestClient restClient;

    //@Value()
    //private String urlBase = "http://httpbin.org";

    public UserClient(@Value("${internal.endpoint.GatewayService}") String urlBase) {
        this.restClient = RestClient.builder()
                .baseUrl(urlBase)
                .build();
    }


    public Boolean userExists(Integer id) {
        try {
            ResponseEntity<Void> responseEntity = restClient.get()
                    .uri("/v1/user-service/api/id/{id}", id)
                    .retrieve()
                    .toBodilessEntity();
            return responseEntity.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }


    public String getUser(Integer id) {

        String x = restClient.get().uri( "/get").retrieve().body(String.class);

        log.info(x);

        return x;

    }

}
