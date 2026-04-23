package br.com.kaikedev.orderservice.Entity.Dto;


import java.util.List;

public class OrderRequest {

    private Integer userId;

    private List<OrderItemRequest> items;


    public OrderRequest(Integer userId, List<OrderItemRequest> items) {
        this.userId = userId;
        this.items = items;
    }

    public OrderRequest() {}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}
