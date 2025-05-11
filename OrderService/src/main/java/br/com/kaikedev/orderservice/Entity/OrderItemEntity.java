package br.com.kaikedev.orderservice.Entity;

public class OrderItemEntity {

    private Integer id;
    private Integer orderId;
    private Integer productId;
    private String productName;
    private Double productUnitPrice;
    private Integer quantity;
    private Double subTotalPrice;

    public OrderItemEntity(Integer id, Integer orderId, Integer productId, String productName, Double productUnitPrice, Integer quantity, Double subTotalPrice) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productUnitPrice = productUnitPrice;
        this.quantity = quantity;
        this.subTotalPrice = subTotalPrice;
    }
    public OrderItemEntity() {}

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(Double productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubTotalPrice() {
        return subTotalPrice;
    }

    public void setSubTotalPrice(Double subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
    }
}
