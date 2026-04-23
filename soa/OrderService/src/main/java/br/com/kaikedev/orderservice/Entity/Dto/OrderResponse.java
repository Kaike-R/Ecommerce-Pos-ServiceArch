package br.com.kaikedev.orderservice.Entity.Dto;

public class OrderResponse {

    public Integer orderId;
    public Integer customer_id;
    public String message;

    public OrderResponse(String message, Integer customer_id) {
        this.message = message;
        this.customer_id = customer_id;
    }

    public OrderResponse() {}

    public OrderResponse(Integer orderId, Integer customer_id, String message) {
        this.orderId = orderId;
        this.customer_id = customer_id;
        this.message = message;
    }

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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "orderId=" + orderId +
                ", customer_id=" + customer_id +
                ", message='" + message + '\'' +
                '}';
    }
}
