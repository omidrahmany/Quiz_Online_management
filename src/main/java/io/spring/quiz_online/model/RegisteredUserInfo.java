package io.spring.quiz_online.model;

public class RegisteredUserInfo {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String role;
    private boolean isEnable;



    public void setRole(String role) {
        this.role = role;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public boolean isEnable() {
        return isEnable;
    }

}
