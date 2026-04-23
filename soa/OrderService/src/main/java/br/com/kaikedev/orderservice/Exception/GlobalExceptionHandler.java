package br.com.kaikedev.orderservice.Exception;


import br.com.kaikedev.orderservice.Entity.Dto.ErrorDetails;
import br.com.kaikedev.orderservice.Entity.Dto.PaymentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorDetails> handleException(Exception exception, WebRequest request) {
//        ErrorDetails errorDetails = new ErrorDetails(
//                LocalDateTime.now(),
//                exception.getMessage(),
//                request.getDescription(false),
//                "ERROR"
//        );
//
//        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    @ExceptionHandler(PaymentException.class)
//    public static ResponseEntity<PaymentResponse> handlePaymentException(Exception exception, WebRequest request) {
//        PaymentResponse paymentResponse = new PaymentResponse();
//        paymentResponse.setMessage(exception.getMessage());
//        return new ResponseEntity<>(paymentResponse, );
//    }
}
