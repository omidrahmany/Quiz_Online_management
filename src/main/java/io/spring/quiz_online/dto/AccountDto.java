package io.spring.quiz_online.dto;

import io.spring.quiz_online.model.RoleEnum;

public class AccountDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String roleType;
    private boolean isEnable;

    public AccountDto(String firstName, String lastName, String username, String email, String roleType, boolean isEnable) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.roleType = roleType;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return roleType;
    }

    public void setRole(RoleEnum role) {
        this.roleType = roleType;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public static AccountDtoBuilder getInstance(){return new AccountDtoBuilder();}

    public static class AccountDtoBuilder {
        private String firstName;
        private String lastName;
        private String username;
        private String email;
        private String roleType;
        private boolean isEnable;

        public AccountDtoBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AccountDtoBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AccountDtoBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public AccountDtoBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public AccountDtoBuilder setRoleType(String roleType) {
            this.roleType = roleType;
            return this;
        }

        public AccountDtoBuilder setIsEnable(boolean isEnable) {
            this.isEnable = isEnable;
            return this;
        }

        public AccountDto createAccountDto() {
            return new AccountDto(firstName, lastName, username, email, roleType, isEnable);
        }
    }
}
