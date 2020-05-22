package io.spring.quiz_online.service;

import io.spring.quiz_online.dto.TeacherDto;
import io.spring.quiz_online.model.Course;
import io.spring.quiz_online.repositories.TeacherRepository;

import java.util.List;

public interface CourseService {
    List<Course> findAll();
    List<TeacherDto> findAllTeachersByAccountEnabled();
}
