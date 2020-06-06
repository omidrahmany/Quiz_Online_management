package io.spring.quiz_online.service;

import io.spring.quiz_online.dto.CourseDto;
import io.spring.quiz_online.dto.CourseDtoForSaving;
import io.spring.quiz_online.dto.StudentDto;
import io.spring.quiz_online.dto.TeacherDto;

import java.util.List;

public interface CourseService {
    List<CourseDto> findAll();
    List<TeacherDto> findAllTeachersByAccountEnabled();
    void saveCourseDto(CourseDtoForSaving courseDtoForSaving);
    List<StudentDto> findAllStudentsByAccountEnabled();
    void deleteCourseById(Long courseId);
    CourseDto findCourseById(Long id);

}
