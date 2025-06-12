package br.com.kaikedev.orderservice.Entity.Dto;

public class UserDto {

    private String name;
    private String cpf;
    private String email;
    private String phone;

    public UserDto(String email, String cpf, String name, String phone) {
        this.email = email;
        this.cpf = cpf;
        this.name = name;
        this.phone = phone;
    }

    public UserDto() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
