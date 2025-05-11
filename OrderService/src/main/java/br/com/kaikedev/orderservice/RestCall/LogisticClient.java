package br.com.kaikedev.orderservice.RestCall;


import br.com.kaikedev.orderservice.Entity.Dto.LogisticRequest;
import br.com.kaikedev.orderservice.Entity.Dto.LogisticResponse;
import org.springframework.stereotype.Component;

@Component
public class LogisticClient {

    public LogisticResponse processShipping(LogisticRequest logisticRequest) {

        return new LogisticResponse();
    }

}
