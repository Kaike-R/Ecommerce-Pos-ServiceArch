package br.com.kaikedev.paymentservice.Service;

import br.com.kaikedev.paymentservice.Entity.Dto.CardDto;
import br.com.kaikedev.paymentservice.Entity.Dto.PaymentRequest;
import br.com.kaikedev.paymentservice.Entity.Dto.PaymentErrorResponse;
import br.com.kaikedev.paymentservice.Entity.Dto.PaymentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService();
    }

    @Test
    void shouldReturnErrorWhenCardNumberIsInvalid() {
        PaymentRequest paymentRequest = new PaymentRequest(
                12345, // orderId
                100.0, // amount
                "CREDIT_CARD", // paymentMethod
                new CardDto("João", "12345", "12", "30", "123", "VISA") // card
        );

        Object response = paymentService.processPayment(paymentRequest);

        assertTrue(response instanceof PaymentErrorResponse);
        PaymentErrorResponse errorResponse = (PaymentErrorResponse) response;
        assertEquals("INVALID_CARD_NUMBER", errorResponse.getError());
        assertEquals("Número do cartão inválido.", errorResponse.getMessage());
    }

    @Test
    void shouldReturnErrorWhenCvvIsInvalid() {
        PaymentRequest paymentRequest = new PaymentRequest(
                12345, // orderId
                100.0, // amount
                "CREDIT_CARD", // paymentMethod
                new CardDto("João", "1234567890123456", "12", "30", "000", "VISA") // card
        );

        Object response = paymentService.processPayment(paymentRequest);

        assertTrue(response instanceof PaymentErrorResponse);
        PaymentErrorResponse errorResponse = (PaymentErrorResponse) response;
        assertEquals("INVALID_CVV", errorResponse.getError());
        assertEquals("Código de segurança inválido.", errorResponse.getMessage());
    }

    @Test
    void shouldReturnErrorWhenCardIsDeclined() {
        PaymentRequest paymentRequest = new PaymentRequest(
                12345, // orderId
                100.0, // amount
                "CREDIT_CARD", // paymentMethod
                new CardDto("João", "9999123456789012", "12", "30", "321","VISA") // card
        );

        Object response = paymentService.processPayment(paymentRequest);

        assertTrue(response instanceof PaymentErrorResponse);
        PaymentErrorResponse errorResponse = (PaymentErrorResponse) response;
        assertEquals("CARD_DECLINED", errorResponse.getError());
        assertEquals("Pagamento recusado pela operadora do cartão.", errorResponse.getMessage());
    }

    @Test
    void shouldReturnErrorWhenInsufficientFunds() {
        PaymentRequest paymentRequest = new PaymentRequest(
                12345, // orderId
                1500.0, // amount
                "CREDIT_CARD", // paymentMethod
                new CardDto("João", "1234567890123456", "12", "30", "123", "VISA") // card
        );

        Object response = paymentService.processPayment(paymentRequest);

        assertTrue(response instanceof PaymentErrorResponse);
        PaymentErrorResponse errorResponse = (PaymentErrorResponse) response;
        assertEquals("INSUFFICIENT_FUNDS", errorResponse.getError());
        assertEquals("Saldo insuficiente para esta transação.", errorResponse.getMessage());
    }

    @Test
    void shouldReturnSuccessWhenPaymentIsValid() {
        PaymentRequest paymentRequest = new PaymentRequest(
                12345, // orderId
                100.0, // amount
                "CREDIT_CARD", // paymentMethod
                new CardDto("João", "1234567890123456", "12", "30", "123", "VISA") // card
        );

        Object response = paymentService.processPayment(paymentRequest);

        assertTrue(response instanceof PaymentResponse);
        PaymentResponse successResponse = (PaymentResponse) response;
        assertNotNull(successResponse.getTransactionId());
        assertEquals("APPROVED", successResponse.getStatus());
    }
}
