package br.com.kaikedev.paymentservice.Entity.Dto;




public class PaymentRequest {

    private Integer orderId;
    private Double amount;
    private String currency;
    private String paymentMethod;
    private CardDto card;
    private UserDto user;

    public PaymentRequest(Integer orderId, Double amount, String currency, String paymentMethod, CardDto card, UserDto user) {
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.card = card;
        this.user = user;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
