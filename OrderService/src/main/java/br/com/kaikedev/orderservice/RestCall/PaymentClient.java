package br.com.kaikedev.orderservice.RestCall;

import br.com.kaikedev.orderservice.Entity.Dto.PaymentRequest;
import br.com.kaikedev.orderservice.Entity.Dto.PaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@Component
public class PaymentClient {

    private static final Logger log = LoggerFactory.getLogger(PaymentClient.class);
    private RestClient restClient;


    public PaymentClient(@Value("${internal.endpoint.GatewayService}") String urlBase) {
        this.restClient = RestClient.builder().baseUrl(urlBase).build();
    }


    public PaymentResponse processPayment(PaymentRequest paymentRequest)  {
        //Thread.sleep(Duration.ofMinutes(1l));
        try {
            ResponseEntity<PaymentResponse> x = restClient.post()
                    .uri("/v1/payment-service/api").body(paymentRequest).retrieve()
//                    .onStatus(
//                            status -> status.is4xxClientError(),
//                            (req, res) -> {
//                                String errorBody = res.getBody().toString();
//                                log.info(errorBody);
//                                PaymentResponse paymentResponse = new PaymentResponse();
//                                paymentResponse.setTransactionId("111");
//                                paymentResponse.setStatus("FAILED");
//                                paymentResponse.setMessage(errorBody);
//                                return paymentResponse;
//                            }
//                    )
                    .toEntity(PaymentResponse.class);
            return x.getBody();
        } catch (Error e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );
        }

    }
}
