package br.com.kaikedev.paymentservice.Controller;

import br.com.kaikedev.paymentservice.Entity.Dto.CardDto;
import br.com.kaikedev.paymentservice.Entity.Dto.PaymentRequest;
import br.com.kaikedev.paymentservice.Entity.Dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnBadRequestWhenCardNumberIsInvalid() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest(
                12345, // orderId
                100.0, // amount
                "CREDIT_CARD", // paymentMethod
                new CardDto("João", "12345", "12", "30", "123", "VISA") // card
        );
        String jsonRequest = objectMapper.writeValueAsString(paymentRequest);

        mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Número do cartão inválido."));
    }

    @Test
    void shouldReturnBadRequestWhenCvvIsInvalid() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest(
                12345, // orderId
                100.0, // amount
                "CREDIT_CARD", // paymentMethod
                new CardDto("João", "1234567890123456", "12", "30", "000", "VISA") // card
        );
        String jsonRequest = objectMapper.writeValueAsString(paymentRequest);

        mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnSuccessWhenPaymentIsValid() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest(
                12345, // orderId
                100.0, // amount
                "CREDIT_CARD", // paymentMethod
                new CardDto("João", "1234567890123456", "12", "30", "123", "VISA") // card
        );
        String jsonRequest = objectMapper.writeValueAsString(paymentRequest);

        mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").isNotEmpty())
                .andExpect(jsonPath("$.status").value("APPROVED"));
    }
}