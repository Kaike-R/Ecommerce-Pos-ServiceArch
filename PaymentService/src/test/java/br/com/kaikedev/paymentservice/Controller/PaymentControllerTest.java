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

    // Teste com número de cartão inválido
    @Test
    void shouldReturnBadRequestWhenCardNumberIsInvalid() throws Exception {
        // Criação de um PaymentRequest com um número de cartão inválido
        PaymentRequest paymentRequest = new PaymentRequest(
                12345, // orderId
                100.0, // amount
                "BRL", // currency
                "CREDIT_CARD", // paymentMethod
                new CardDto("João", "12345", "12", "30", "123", "VISA"), // card
                new UserDto("João Silva", "joao@example.com","name","213124324232") // user
        );
        String jsonRequest = objectMapper.writeValueAsString(paymentRequest);

        mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest()) // Espera um erro 400
                .andExpect(jsonPath("$.message").value("Número do cartão inválido."));
    }

    // Teste com CVV inválido
    @Test
    void shouldReturnBadRequestWhenCvvIsInvalid() throws Exception {
        // Criação de um PaymentRequest com um CVV inválido
        PaymentRequest paymentRequest = new PaymentRequest(
                12345, // orderId
                100.0, // amount
                "BRL", // currency
                "CREDIT_CARD", // paymentMethod
                new CardDto("João", "1234567890123456", "12", "30", "000", "VISA"), // card
                new UserDto("João Silva", "joao@example.com","11","1109843274924") // user
        );
        String jsonRequest = objectMapper.writeValueAsString(paymentRequest);

        mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest()); // Espera um erro 400
    }

    // Teste com dados válidos
    @Test
    void shouldReturnSuccessWhenPaymentIsValid() throws Exception {
        // Criação de um PaymentRequest válido
        PaymentRequest paymentRequest = new PaymentRequest(
                12345, // orderId
                100.0, // amount
                "BRL", // currency
                "CREDIT_CARD", // paymentMethod
                new CardDto("João", "1234567890123456", "12", "30", "123", "VISA"), // card
                new UserDto("João Silva", "joao@example.com", "123", "123123120414") // user
        );
        String jsonRequest = objectMapper.writeValueAsString(paymentRequest);

        mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk()) // Espera um status 200
                .andExpect(jsonPath("$.transactionId").isNotEmpty()) // Espera que o transactionId não esteja vazio
                .andExpect(jsonPath("$.status").value("APPROVED"));
    }
}