package io.spring.quiz_online.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "managerId")
@DiscriminatorValue("MANAGER")
public class Manager extends Person {
    public Manager() {
    }

    public Manager(String firstName, String lastName, Account account) {
        super(firstName, lastName, account);
    }

}
