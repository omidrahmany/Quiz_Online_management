package io.spring.quiz_online.model;


import javax.persistence.*;
import java.util.Set;


@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    private RoleEnum roleType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private Set<Account> accountSet;

    public Role() {
    }

    public Role(RoleEnum role, Set<Account> accountSet) {
        this.roleType = role;
        this.accountSet = accountSet;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public RoleEnum getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleEnum roleType) {
        this.roleType = roleType;
    }

    public Set<Account> getAccountSet() {
        return accountSet;
    }

    public void setAccountSet(Set<Account> accountSet) {
        this.accountSet = accountSet;
    }
}
