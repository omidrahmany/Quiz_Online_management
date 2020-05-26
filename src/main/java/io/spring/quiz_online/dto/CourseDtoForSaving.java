package io.spring.quiz_online.dto;

public class CourseDtoForSaving {
    private String courseTitle;
    private String startDateJalali;
    private String finishDateJalali;
    private Long teacherId;

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
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

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
