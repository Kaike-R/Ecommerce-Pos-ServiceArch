package br.com.kaikedev.orderservice.RestCall;


import br.com.kaikedev.orderservice.Entity.Dto.LogisticRequest;
import br.com.kaikedev.orderservice.Entity.Dto.LogisticResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Component
public class LogisticClient {


    private static final Logger log = LoggerFactory.getLogger(LogisticClient.class);
    private RestClient restClient;


    public LogisticClient(@Value("${internal.endpoint.GatewayService}") String urlBase) {
        this.restClient = RestClient.builder().baseUrl(urlBase).build();
    }

    public LogisticResponse processShipping(LogisticRequest logisticRequest) {
        log.info(logisticRequest.toString());
        try {
            ResponseEntity<LogisticResponse> x = restClient.post().uri("/v1/logistic-service/api").body(logisticRequest).retrieve()
                    .toEntity(LogisticResponse.class);
            return x.getBody();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }

    }

}
