package io.spring.quiz_online.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "studentId")
@DiscriminatorValue("STUDENT")
public class Student extends Person {

    @ManyToMany(mappedBy = "students")
    private List<Course> courses;

    public Student() {
    }

    public Student(List<Course> courses) {
        this.courses = courses;
    }

    public Student(String firstName, String lastName, Account account, List<Course> courses) {
        super(firstName, lastName, account);
        this.courses = courses;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
