package br.com.kaikedev.orderservice.Entity.Dto;

public class LogisticResponse {

    private String status; // sucess
    private String message;
    private Boolean isDeliveary;
    private String transactionId;

    public LogisticResponse(String message, String status, Boolean isDeliveary, String transactionId) {
        this.message = message;
        this.status = status;
        this.isDeliveary = isDeliveary;
        this.transactionId = transactionId;
    }

    public LogisticResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean isDeliveary() {
        return isDeliveary;
    }

    public void setDeliveary(Boolean deliveary) {
        isDeliveary = deliveary;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
