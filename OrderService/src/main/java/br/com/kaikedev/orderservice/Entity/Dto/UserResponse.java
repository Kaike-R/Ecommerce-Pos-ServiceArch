package br.com.kaikedev.orderservice.Entity.Dto;

public class UserResponse {

    private String status;

    public UserResponse(String status) {
        this.status = status;
    }
    public UserResponse() {}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
