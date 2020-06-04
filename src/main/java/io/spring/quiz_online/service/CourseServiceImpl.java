package io.spring.quiz_online.service;

import com.ibm.icu.text.SimpleDateFormat;
import io.spring.quiz_online.dto.CourseDto;
import io.spring.quiz_online.dto.CourseDtoForSaving;
import io.spring.quiz_online.dto.StudentDto;
import io.spring.quiz_online.dto.TeacherDto;
import io.spring.quiz_online.model.Course;
import io.spring.quiz_online.model.Student;
import io.spring.quiz_online.model.Teacher;
import io.spring.quiz_online.repositories.CourseRepository;
import io.spring.quiz_online.repositories.StudentRepository;
import io.spring.quiz_online.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CourseServiceImpl implements CourseService {

    private TeacherRepository teacherRepository;
    private CourseRepository courseRepository;
    private StudentRepository studentRepository;
    private SimpleDateFormat persianDateFormat;

    public CourseServiceImpl(SimpleDateFormat persianDateFormat, TeacherRepository teacherRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.persianDateFormat = persianDateFormat;
    }

    @Override
    public List<CourseDto> findAll() {
        return courseRepository.findAll()
                .stream()
                .map(mapCourseToCourseDtoFunction())
                .collect(Collectors.toList());
    }

    public Function<Course, CourseDto> mapCourseToCourseDtoFunction() {
        return course -> {

            /* there is "TeacherDto" (not "Teacher"!) inside "CourseDto",
             *  so at first, we should map teacher to teacherDto*/
            Teacher teacher = course.getTeacher();
            TeacherDto teacherDto = new TeacherDto(teacher.getPersonId(), teacher.getFirstName(), teacher.getLastName());

            /* mapping student List to studentDto List*/
            Set<StudentDto> studentDtos;
            if (course.getStudents().size() != 0) {
                studentDtos = course.getStudents()
                        .stream()
                        .map(mapStudentToStudentDtoFunction())
                        .collect(Collectors.toSet());
            } else studentDtos = new HashSet<>();

            return new CourseDto(course.getCourseId(),
                    course.getCourseTitle(),
                    teacherDto,
                    course.getStartDate(),
                    course.getFinishDate(),
                    studentDtos,
                    studentDtos.size());
        };
    }

    private Function<Teacher, TeacherDto> mapTeacherToTeacherDtoFunction() {
        return teacher ->
                new TeacherDto(teacher.getPersonId(), teacher.getFirstName(), teacher.getLastName());
    }

    private Function<Student, StudentDto> mapStudentToStudentDtoFunction() {
        return student ->
                new StudentDto(student.getPersonId(), student.getFirstName(), student.getLastName() , student.getAccount().getEmail());
    }

    @Override
    public List<TeacherDto> findAllTeachersByAccountEnabled() {
        return teacherRepository
                .findAllByAccount_Enabled(true)
                .stream()
                .map(mapTeacherToTeacherDtoFunction())
                .collect(Collectors.toList());

    }


    /* creating new course without assigning students*/
    @Override
    public void saveCourseDto(CourseDtoForSaving courseDtoForSaving) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(courseDtoForSaving.getTeacherId());
        Course course = new Course(courseDtoForSaving.getCourseTitle(), courseDtoForSaving.getStartDateJalali()
                , courseDtoForSaving.getFinishDateJalali(),null , optionalTeacher.get());
        courseRepository.save(course);
    }

    @Override
    public List<StudentDto> findAllStudentsByAccountEnabled() {
        List<Student> students = studentRepository.findAllByAccount_Enabled(true);
        return studentRepository
                .findAllByAccount_Enabled(true)
                .stream()
                .map(mapStudentToStudentDtoFunction())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCourseById(Long courseId) {
        courseRepository.deleteById(courseId);
    }


}
