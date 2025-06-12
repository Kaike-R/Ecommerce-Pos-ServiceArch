package br.com.kaikedev.paymentservice.Entity.Dto;




public class PaymentRequest {

    private Integer orderId;
    private Double amount;
    private String paymentMethod;
    private CardDto card;

    public PaymentRequest(Integer orderId, Double amount, String paymentMethod, CardDto card) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.card = card;
    }

    public PaymentRequest() {}


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public CardDto getCard() {
        return card;
    }

    public void setCard(CardDto card) {
        this.card = card;
    }
}
