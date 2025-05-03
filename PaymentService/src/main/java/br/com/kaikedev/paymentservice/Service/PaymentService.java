package br.com.kaikedev.paymentservice.Service;


import br.com.kaikedev.paymentservice.Entity.Dto.PaymentErrorResponse;
import br.com.kaikedev.paymentservice.Entity.Dto.PaymentRequest;
import br.com.kaikedev.paymentservice.Entity.Dto.PaymentSuccessResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {


    public Object processPayment(PaymentRequest paymentRequest) {

        if (paymentRequest.getCard() == null || paymentRequest.getCard().getNumber().length() < 16) {
            return new PaymentErrorResponse("INVALID_CARD_NUMBER", "Número do cartão inválido.", 400);
        }

        if ("000".equals(paymentRequest.getCard().getCvv())) {
            return new PaymentErrorResponse("INVALID_CVV", "Código de segurança inválido.", 400);
        }

        if (paymentRequest.getCard().getNumber().startsWith("9999")) {
            return new PaymentErrorResponse("CARD_DECLINED", "Pagamento recusado pela operadora do cartão.", 402);
        }

        if (paymentRequest.getAmount() > 1000.0) {
            return new PaymentErrorResponse("INSUFFICIENT_FUNDS", "Saldo insuficiente para esta transação.", 402);
        }

        // Simula sucesso
        String transactionId = UUID.randomUUID().toString();
        return new PaymentSuccessResponse(transactionId, "APPROVED");
    }


}
