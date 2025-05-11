package br.com.kaikedev.orderservice.RestCall;

import br.com.kaikedev.orderservice.Entity.Dto.PaymentRequest;
import br.com.kaikedev.orderservice.Entity.Dto.PaymentResponse;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class PaymentClient {


    public PaymentResponse processPayment(PaymentRequest paymentRequest) throws InterruptedException {
        Thread.sleep(Duration.ofMinutes(1l));
        return new PaymentResponse(true, "Sucesso", "1");
    }
}
