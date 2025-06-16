package br.com.kaikedev.orderservice.Entity.Dto;

public class LogisticRequest {

    private Integer orderId;
    private Integer userId;
    private String address;
    private String city;
    private String state;
    private String zip;


    public LogisticRequest(Integer orderId, Integer userId, String address, String city, String state, String zip) {
        this.orderId = orderId;
        this.userId = userId;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
    public LogisticRequest() {}

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "LogisticRequest{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
