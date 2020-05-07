package io.spring.quiz_online.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "managerId")
public class Manager extends Person {
}
