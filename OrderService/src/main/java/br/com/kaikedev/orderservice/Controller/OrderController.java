package br.com.kaikedev.orderservice.Controller;


import br.com.kaikedev.orderservice.Entity.Dto.*;
import br.com.kaikedev.orderservice.Entity.Enum.OrderEnum;
import br.com.kaikedev.orderservice.Entity.OrderEntity;
import br.com.kaikedev.orderservice.Service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {


    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) throws Exception {

        OrderEntity orderEntity = orderService.createOrder(orderRequest);

        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setMessage(orderEntity.getStatus().toString());

        orderResponse.setCustomer_id( orderEntity.getCustomerId());

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);

    }

    @PostMapping("/pay")
    public ResponseEntity<PaymentResponse> payOrder(@RequestBody PaymentRequest paymentRequest) throws Exception {

        OrderEntity orderEntity = orderService.payOrder(paymentRequest);

        PaymentResponse paymentResponse = new PaymentResponse();

        //paymentResponse.setMessage(orderEntity.getStatus().toString());
        paymentResponse.setTransactionId(orderEntity.getId().toString());
        paymentResponse.setStatus(orderEntity.getStatus().toString());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(paymentResponse);

    }

    @PostMapping("/ship")
    public ResponseEntity<LogisticResponse> logisticOrder(@RequestBody LogisticRequest orderRequest) throws Exception {

        OrderEntity orderEntity = orderService.logisticOrder(orderRequest);

        LogisticResponse logisticResponse = new LogisticResponse();

        logisticResponse.setMessage(orderEntity.getStatus().toString());
        logisticResponse.setTransactionId(orderEntity.getId().toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(logisticResponse);

    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> getOrder(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<OrderEntity>> getAllOrders(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(orderService.getOrdersByUserId(id));
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Integer id, @RequestParam OrderEnum status) throws Exception {

        OrderResponse orderResponse = new OrderResponse();

        OrderEntity orderEntity = orderService.updateOrderStatus(id, status);

        orderResponse.setCustomer_id(orderEntity.getCustomerId());

        orderResponse.setMessage(status.toString());

        return ResponseEntity.ok(orderResponse);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Integer id) throws Exception {
        OrderResponse orderResponse = new OrderResponse();
        Boolean resp = orderService.cancelOrder(id);
        orderResponse.setMessage("Deletado ou não");
        orderResponse.setCustomer_id(id);

        if (!resp) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(orderResponse);
        }

        return ResponseEntity.ok(orderResponse);
    }
}
