package br.com.kaikedev.logisticservice.Dto;


public class LogisticRequest {
    private Integer order_id;
    private Integer user_id;
    private String address;
    private String city;
    private String state;
    private String zip;


    public LogisticRequest(Integer order_id, Integer user_id, String address, String city, String state, String zip) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public LogisticRequest() {
    }

    public Integer getOrderId() {
        return order_id;
    }

    public void setOrderId(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
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
}
