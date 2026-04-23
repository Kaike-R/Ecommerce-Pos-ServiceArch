package br.com.kaikedev.paymentservice.Service;


import br.com.kaikedev.paymentservice.Entity.Dto.PaymentErrorResponse;
import br.com.kaikedev.paymentservice.Entity.Dto.PaymentRequest;
import br.com.kaikedev.paymentservice.Entity.Dto.PaymentResponse;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class PaymentService {


    public PaymentResponse processPayment(PaymentRequest paymentRequest) {

        String transactionId = UUID.randomUUID().toString();

//        if (paymentRequest.getCard() == null || paymentRequest.getCard().getNumber().length() < 16) {
//            return new PaymentResponse(null, 400, "INVALID_CARD_NUMBER");
//        }
//
//        if ("000".equals(paymentRequest.getCard().getCvv())) {
//            return new PaymentResponse(null, 400, "INVALID_CVV");
//        }
//
//        if (paymentRequest.getCard().getNumber().startsWith("9999")) {
//            return new PaymentResponse(null, 402, "CARD_DECLINED");
//        }
//
//        if (paymentRequest.getAmount() > 1000.0) {
//            return new PaymentResponse(null, 402, "INSUFFICIENT_FUNDS");
//        }

        if (Objects.equals(paymentRequest.getPaymentMethod(), "credit_card")) {
            return new PaymentResponse(transactionId, 400, "INSUFFICIENT_FUNDS");
        }

        // Simula sucesso

        return new PaymentResponse(transactionId, 200, "APPROVED");
    }


}
