package io.spring.quiz_online.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;


@Entity
@PrimaryKeyJoinColumn(name = "teacherId")
@DiscriminatorValue("TEACHER")
public class Teacher extends Person {



    public Teacher() {
    }

    public Teacher(List<Course> courses) {
        this.courses = courses;
    }

    public Teacher(String firstName, String lastName, Account account, List<Course> courses) {
        super(firstName, lastName, account);
        this.courses = courses;
    }


    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

}
