package br.com.kaikedev.orderservice.Service;


import br.com.kaikedev.orderservice.Entity.Dto.*;
import br.com.kaikedev.orderservice.Entity.Enum.OrderEnum;
import br.com.kaikedev.orderservice.Entity.OrderEntity;
import br.com.kaikedev.orderservice.Entity.OrderItemEntity;
import br.com.kaikedev.orderservice.Repository.OrderRepository;
import br.com.kaikedev.orderservice.RestCall.LogisticClient;
import br.com.kaikedev.orderservice.RestCall.PaymentClient;
import br.com.kaikedev.orderservice.RestCall.ProductClient;
import br.com.kaikedev.orderservice.RestCall.UserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.InsufficientResourcesException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private OrderRepository orderRepository;
    private ProductClient productClient;
    private UserClient userClient;
    private PaymentClient paymentClient;
    private LogisticClient logisticClient;

    public OrderService(OrderRepository orderRepository, ProductClient productClient, PaymentClient paymentClient, LogisticClient logisticClient, UserClient userClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
        this.paymentClient = paymentClient;
        this.logisticClient = logisticClient;
        this.userClient = userClient;
    }

    @Transactional
    public OrderEntity createOrder(OrderRequest orderRequest) throws Exception {

        if (!userClient.userExists(orderRequest.getUserId())) {
            throw new IllegalArgumentException("User not found");
        }

        for(OrderItemRequest item : orderRequest.getItems()) {
            Boolean inStock = productClient.CheckStock(item.getProductId(), item.getQuantity());
            if (!inStock) {
                throw new Exception("Product: " + item.getProductId() + "dont have stock");
            }
        }

        OrderEntity order = new OrderEntity();
        order.setCustomerId(orderRequest.getUserId());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderEnum.CREATED);


        List<OrderItemEntity> items = new ArrayList<>();
        Double total = 0d;

        for (OrderItemRequest itemRequest : orderRequest.getItems()) {
            ProductDto product = productClient.getProduct(itemRequest.getProductId());

            log.info("depois do getProduct");
            OrderItemEntity item = new OrderItemEntity();
            item.setProductId(product.getId());
            item.setQuantity(itemRequest.getQuantity());
            item.setProductName(product.getName());
            item.setProductUnitPrice(product.getPrice());
            item.setSubTotalPrice(product.getPrice() * itemRequest.getQuantity());

            items.add(item);
            total += item.getSubTotalPrice();

            productClient.updateStock(product.getId(), itemRequest.getQuantity());


        }

        order.setItens(items);
        order.setTotalAmount(total);
        orderRepository.save(order);

        return order;

    }

    @Transactional
    public OrderEntity payOrder(PaymentRequest paymentRequest) throws Exception {

        OrderEntity order = orderRepository.findById(paymentRequest.getOrderId()).orElseThrow(
                () -> new InsufficientResourcesException("Order not found")
        );

        log.info("depois do getOrder");
        log.info(paymentRequest.toString());
        log.info(order.toString());

        Double amount = order.getTotalAmount();
        paymentRequest.setAmount(amount);



//        if (Objects.equals(paymentResponse.getStatus(), "APPROVED"))
//        {
//            order.setStatus(OrderEnum.PAID);
//            orderRepository.save(order);
//        } else {
//            order.setStatus(OrderEnum.PAYMENT_FAILED);
//            orderRepository.save(order);
//            compensateStock(order.getItens());
//            throw new Exception("payment failure");
//        }

        try {
            PaymentResponse paymentResponse = paymentClient.processPayment(paymentRequest);
            order.setStatus(OrderEnum.PAID);
            orderRepository.save(order);
        } catch (Exception e) {
            order.setStatus(OrderEnum.PAYMENT_FAILED);
            orderRepository.save(order);
            compensateStock(order.getItens());
            throw new Exception("payment failure");
        }

        return order;
    }

    @Transactional
    public OrderEntity logisticOrder(LogisticRequest logisticRequest) throws Exception {

        OrderEntity order = orderRepository.findById(logisticRequest.getOrderId()).orElseThrow(
                () -> new InsufficientResourcesException("Order not found")
        );
        LogisticResponse logisticResponse;
        try {
            order.setStatus(OrderEnum.PROCESSING);
            orderRepository.save(order);
            log.info(logisticRequest.toString());
            logisticResponse = logisticClient.processShipping(logisticRequest);
        } catch (Exception e) {
            order.setStatus(OrderEnum.PROCESSING_FAILED);
            orderRepository.save(order);
            throw new Exception(e);

        }

        if (logisticResponse.isDeliveary())
        {
            order.setStatus(OrderEnum.DELIVERED);
            orderRepository.save(order);
        } else {
            order.setStatus(OrderEnum.CANCELLED);
            orderRepository.save(order);
            compensateStock(order.getItens());
            throw new Exception("deliveary failure");
        }

        return order;
    }


    private void compensateStock(List<OrderItemEntity> items) {
        for (OrderItemEntity item : items) {
            productClient.updateStock(item.getProductId(), item.getQuantity());
        }
    }

    @Transactional
    public OrderEntity updateOrderStatus(Integer id, OrderEnum orderStatus) throws Exception {
        OrderEntity orderEntity = getOrderById(id);
        orderEntity.setStatus(orderStatus);
        return orderRepository.save(orderEntity);
    }

    public OrderEntity getOrderById(Integer orderId) throws Exception {
        return orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not Found"));
    }

    public List<OrderEntity> getOrdersByUserId(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    @Transactional
    public Boolean cancelOrder(Integer id) throws Exception {
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(
                () -> new InsufficientResourcesException("Order not found")
        );


//        if (!orderEntity.getStatus().equals(OrderEnum.CANCELLED)) {
//            throw new Exception("Order Cannot be cancel" + orderEntity.getStatus());
//        }

        compensateStock(orderEntity.getItens());
        orderEntity.setStatus(OrderEnum.CANCELLED);
        orderRepository.save(orderEntity);
        return true;
    }
}
