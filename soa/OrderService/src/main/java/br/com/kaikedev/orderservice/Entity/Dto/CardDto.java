package br.com.kaikedev.orderservice.Entity.Dto;

public class CardDto {

    private String holderName;
    private String number;
    private String expiryMonth;
    private String expiryYear;
    private String cvv; //os 3 numeros do verso do cartao
    private String brand; // bandeira

    public CardDto(String holderName, String number, String expiryYear, String expiryMonth, String cvv, String brand) {
        this.holderName = holderName;
        this.number = number;
        this.expiryYear = expiryYear;
        this.expiryMonth = expiryMonth;
        this.cvv = cvv;
        this.brand = brand;
    }

    public CardDto() {}

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}
