package io.spring.quiz_online.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "studentId")
public class Student extends Person {

    public Student(String firstName, String lastName, Account account) {
        super(firstName, lastName, account);
    }
}
