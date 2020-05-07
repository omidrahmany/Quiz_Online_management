package io.spring.quiz_online.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity
@PrimaryKeyJoinColumn(name = "teacherId")
@DiscriminatorValue("TEACHER")
public class Teacher extends Person {

    public Teacher(String firstName, String lastName, Account account) {
        super(firstName, lastName, account);
    }

    public Teacher() {
    }
}
