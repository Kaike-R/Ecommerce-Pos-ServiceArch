package br.com.kaikedev.orderservice.Entity.Dto;

public class OrderItemRequest {

    private Integer productId;
    private Integer quantity;

    public OrderItemRequest() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
