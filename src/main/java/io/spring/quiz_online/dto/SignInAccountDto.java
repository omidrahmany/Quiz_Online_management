package io.spring.quiz_online.dto;

public class SignInAccountDto {
    private String username;
    private String password;

    public SignInAccountDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
