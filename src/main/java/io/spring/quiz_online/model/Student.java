package io.spring.quiz_online.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "studentId")
@DiscriminatorValue("STUDENT")
public class Student extends Person {

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses = new HashSet<>();

    public Student() {
    }

    public Student(Set<Course> courses) {
        this.courses = courses;
    }

    public Student(String firstName, String lastName, Account account, Set<Course> courses) {
        super(firstName, lastName, account);
        this.courses = courses;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(courses, student.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courses);
    }
}
