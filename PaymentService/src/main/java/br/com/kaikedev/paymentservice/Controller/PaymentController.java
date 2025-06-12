package br.com.kaikedev.paymentservice.Controller;


import br.com.kaikedev.paymentservice.Entity.Dto.PaymentErrorResponse;
import br.com.kaikedev.paymentservice.Entity.Dto.PaymentRequest;
import br.com.kaikedev.paymentservice.Entity.Dto.PaymentResponse;
import br.com.kaikedev.paymentservice.Service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> ProcessPayment(@RequestBody PaymentRequest paymentRequest)
    {
        PaymentResponse response = paymentService.processPayment(paymentRequest);

        if(!HttpStatus.valueOf(response.getStatus()).is2xxSuccessful()) {
            return ResponseEntity.status(response.getStatus()).body(response);
        }
        return ResponseEntity.status(response.getStatus()).body(response);
    }


}
