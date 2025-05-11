package br.com.kaikedev.orderservice.Entity.Dto;

public class OrderResponse {

    public Integer customer_id;
    public String message;

    public OrderResponse(String message, Integer customer_id) {
        this.message = message;
        this.customer_id = customer_id;
    }

    public OrderResponse() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }
}
