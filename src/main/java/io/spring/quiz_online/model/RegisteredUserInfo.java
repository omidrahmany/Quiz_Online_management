package io.spring.quiz_online.model;

public class RegisteredUserInfo {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String role;
    private boolean isEnable;

    public RegisteredUserInfo(String firstName, String lastName, String username, String password, String email, String role, boolean isEnable) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.isEnable = isEnable;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public static RegisteredUserInfoBuilder getRegisteredUserInfoBuilder(){return new RegisteredUserInfoBuilder();}

    public static class RegisteredUserInfoBuilder {
        private String firstName;
        private String lastName;
        private String username;
        private String password;
        private String email;
        private String role;
        private boolean isEnable;

        public RegisteredUserInfoBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public RegisteredUserInfoBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public RegisteredUserInfoBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public RegisteredUserInfoBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public RegisteredUserInfoBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public RegisteredUserInfoBuilder setRole(String role) {
            this.role = role;
            return this;
        }

        public RegisteredUserInfoBuilder setIsEnable(boolean isEnable) {
            this.isEnable = isEnable;
            return this;
        }

        public RegisteredUserInfo createRegisteredUserInfo() {
            return new RegisteredUserInfo(firstName, lastName, username, password, email, role, isEnable);
        }
    }
}
