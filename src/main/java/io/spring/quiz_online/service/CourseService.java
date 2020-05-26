package io.spring.quiz_online.service;

import io.spring.quiz_online.dto.CourseDtoForSaving;
import io.spring.quiz_online.dto.StudentDto;
import io.spring.quiz_online.dto.TeacherDto;
import io.spring.quiz_online.model.Course;

import java.util.Date;
import java.util.List;

public interface CourseService {
    List<Course> findAll();
    List<TeacherDto> findAllTeachersByAccountEnabled();
    void saveCourseDto(CourseDtoForSaving courseDtoForSaving);
    List<StudentDto> findAllStudentsByAccountEnabled();

}
