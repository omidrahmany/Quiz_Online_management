package io.spring.quiz_online.dto;

public class TeacherDto {
    private Long teacherId;
    private String firstName;
    private String lastName;

    public TeacherDto(Long teacherId, String firstName, String lastName) {
        this.teacherId = teacherId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
