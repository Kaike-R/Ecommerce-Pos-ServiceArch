package br.com.kaikedev.paymentservice.Controller;


import br.com.kaikedev.paymentservice.Entity.Dto.PaymentErrorResponse;
import br.com.kaikedev.paymentservice.Entity.Dto.PaymentRequest;
import br.com.kaikedev.paymentservice.Service.PaymentService;
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
    public ResponseEntity<?> ProcessPayment(@RequestBody PaymentRequest paymentRequest)
    {
        Object response = paymentService.processPayment(paymentRequest);

        if(response instanceof PaymentErrorResponse errorResponse) {
            return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
        }
        return ResponseEntity.ok(response);
    }


}
