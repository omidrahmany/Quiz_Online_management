package io.spring.quiz_online.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

public class CourseDto {

    private Long courseId;

    private String courseTitle;

    @JsonProperty("teacher")
    private TeacherDto teacherDto;

    @JsonProperty("startDate")
    private String startDateJalali;

    @JsonProperty("finishDate")
    private String finishDateJalali;

    @JsonProperty("students")
    private Set<StudentDto> studentDtoList;

    private int numberOfStudentsAssigned;

    public CourseDto() {
    }

    public CourseDto(Long courseId, String courseTitle, TeacherDto teacherDto, String startDateJalali, String finishDateJalali, Set<StudentDto> studentDtoList, int numberOfStudentsAssigned) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.teacherDto = teacherDto;
        this.startDateJalali = startDateJalali;
        this.finishDateJalali = finishDateJalali;
        this.studentDtoList = studentDtoList;
        this.numberOfStudentsAssigned = numberOfStudentsAssigned;
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

    public TeacherDto getTeacherDto() {
        return teacherDto;
    }

    public void setTeacherDto(TeacherDto teacherDto) {
        this.teacherDto = teacherDto;
    }

    public String getStartDateJalali() {
        return startDateJalali;
    }

    public void setStartDateJalali(String startDateJalali) {
        this.startDateJalali = startDateJalali;
    }

    public String getFinishDateJalali() {
        return finishDateJalali;
    }

    public void setFinishDateJalali(String finishDateJalali) {
        this.finishDateJalali = finishDateJalali;
    }

    public Set<StudentDto> getStudentDtoList() {
        return studentDtoList;
    }

    public void setStudentDtoList(Set<StudentDto> studentDtoList) {
        this.studentDtoList = studentDtoList;
    }

    public int getNumberOfStudentsAssigned() {
        return numberOfStudentsAssigned;
    }

    public void setNumberOfStudentsAssigned(int numberOfStudentsAssigned) {
        this.numberOfStudentsAssigned = numberOfStudentsAssigned;
    }

    public static CourseDtoBuilder getCourseDtoBuilder(){
        return new CourseDtoBuilder();
    }

    public static class CourseDtoBuilder {
        private Long courseId;
        private String courseTitle;
        private TeacherDto teacher;
        private String startDateJalali;
        private String finishDateJalali;
        private Set<StudentDto> students;
        private int numberOfStudents;

        public CourseDtoBuilder setCourseId(Long courseId) {
            this.courseId = courseId;
            return this;
        }

        public CourseDtoBuilder setCourseTitle(String courseTitle) {
            this.courseTitle = courseTitle;
            return this;
        }

        public CourseDtoBuilder setTeacher(TeacherDto teacher) {
            this.teacher = teacher;
            return this;
        }

        public CourseDtoBuilder setStartDateJalali(String startDateJalali) {
            this.startDateJalali = startDateJalali;
            return this;
        }

        public CourseDtoBuilder setFinishDateJalali(String finishDateJalali) {
            this.finishDateJalali = finishDateJalali;
            return this;
        }

        public CourseDtoBuilder setStudents(Set<StudentDto> students) {
            this.students = students;
            return this;
        }

        public CourseDtoBuilder setNumberOfStudents(int numberOfStudents) {
            this.numberOfStudents = numberOfStudents;
            return this;
        }

        public CourseDto createCourseDto() {
            return new CourseDto(courseId, courseTitle, teacher, startDateJalali, finishDateJalali, students, numberOfStudents);
        }
    }
}
