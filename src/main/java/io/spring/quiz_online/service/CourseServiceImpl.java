package io.spring.quiz_online.service;

import io.spring.quiz_online.dto.TeacherDto;
import io.spring.quiz_online.model.Course;
import io.spring.quiz_online.model.Teacher;
import io.spring.quiz_online.repositories.CourseRepository;
import io.spring.quiz_online.repositories.StudentRepository;
import io.spring.quiz_online.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private TeacherRepository teacherRepository;
    private CourseRepository courseRepository;
    private StudentRepository studentRepository;

    public CourseServiceImpl(TeacherRepository teacherRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    private Function<Teacher, TeacherDto> mapTeacherToTeacherDtoFunction() {
        return teacher ->
                new TeacherDto(teacher.getPersonId(), teacher.getFirstName(), teacher.getLastName());
    }

    @Override
    public List<TeacherDto> findAllTeachersByAccountEnabled() {
        return teacherRepository
                .findAllByAccount_Enabled(true)
                .stream()
                .map(mapTeacherToTeacherDtoFunction())
                .collect(Collectors.toList());

    }
}