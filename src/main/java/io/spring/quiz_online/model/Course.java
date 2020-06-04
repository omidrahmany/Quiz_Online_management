package io.spring.quiz_online.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    private String courseTitle;
    private String startDate;
    private String finishDate;

    /* fetch type should be FetchType.EAGER so don't change to FetchType.LAZY one.
    * otherwise the LazyInitializationException is thrown */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "course_student",
            joinColumns = @JoinColumn(name = "courseId"),
            inverseJoinColumns =@JoinColumn(name = "studentId"))
    private List<Student> students;

    @ManyToOne
    @JoinColumn(name = "teacherId")
    private Teacher teacher;

    public Course() {
    }

    public Course(String courseTitle, String startDate, String finishDate, List<Student> students, Teacher teacher) {
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.students = students;
        this.teacher = teacher;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseId.equals(course.courseId) &&
                courseTitle.equals(course.courseTitle) &&
                startDate.equals(course.startDate) &&
                finishDate.equals(course.finishDate) &&
                Objects.equals(students, course.students) &&
                teacher.equals(course.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseTitle, startDate, finishDate, teacher);
    }
}
