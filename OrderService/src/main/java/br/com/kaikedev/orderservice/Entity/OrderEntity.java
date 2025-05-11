package br.com.kaikedev.orderservice.Entity;

import br.com.kaikedev.orderservice.Entity.Enum.OrderEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderEntity {

    private Integer id;
    private Integer customerId;
    private LocalDateTime orderDate;
    private OrderEnum status; // "CREATED", "PAID", "SHIPPED", "DELIVERED", "CANCELLED"
    private Double totalAmount;
    private List<OrderItemEntity> itens;

    public OrderEntity(Integer id, Integer customerId, LocalDateTime orderDate, OrderEnum status, Double totalAmount, List<OrderItemEntity> itens) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.itens = itens;
    }
    public OrderEntity() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderEnum getStatus() {
        return status;
    }

    public void setStatus(OrderEnum status) {
        this.status = status;
    }

    public void setStatus(String status){
        this.status = OrderEnum.valueOf(status);
    }

    public List<OrderItemEntity> getItens() {
        return itens;
    }

    public void setItens(List<OrderItemEntity> itens) {
        this.itens = itens;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
