package br.com.kaikedev.gateway.Service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {

    @Value("${endpoint.loginMock}")
    private String endpoint;

    RestClient restClient = RestClient.create();



    public String getTokenFromService(String token) {
        return restClient
                .get()
                .uri(endpoint)
                .header("Authorization", token)
                .retrieve()
                .body(String.class);
    }


}
