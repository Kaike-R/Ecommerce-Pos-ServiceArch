package br.com.kaikedev.auth.ValueObject;



public record RegisterRequest(String email, String password, UserRole role) {
}
