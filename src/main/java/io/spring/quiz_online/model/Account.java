package io.spring.quiz_online.model;


import javax.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "personId")
    private Person person;

    @ManyToOne(optional = false)
    @JoinColumn(name = "roleId")
    private Role role;

    private boolean enabled;

    public Account() {
    }

    public Account(String username, String password, String email, Person person, Role role, boolean enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.person = person;
        this.role = role;
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Builder

    public static AccountBuilder getAccountBuilder(){return new AccountBuilder() ;}


     public static class AccountBuilder {
        private String username;
        private String password;
        private String email;
        private Person person;
        private Role role;
        private boolean enabled;

        public AccountBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public AccountBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public AccountBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public AccountBuilder setPerson(Person person) {
            this.person = person;
            return this;
        }

        public AccountBuilder setRole(Role role) {
            this.role = role;
            return this;
        }

        public AccountBuilder setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Account createAccount() {
            return new Account(username, password, email, person, role, enabled);
        }
    }


}
