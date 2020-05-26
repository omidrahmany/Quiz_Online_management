package io.spring.quiz_online.service;

import com.ibm.icu.text.SimpleDateFormat;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private TeacherRepository teacherRepository;
    private CourseRepository courseRepository;
    private StudentRepository studentRepository;
    private SimpleDateFormat persianDateFormat;

    public CourseServiceImpl(SimpleDateFormat persianDateFormat,TeacherRepository teacherRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.persianDateFormat = persianDateFormat;
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    private Function<Teacher, TeacherDto> mapTeacherToTeacherDtoFunction() {
        return teacher ->
                new TeacherDto(teacher.getPersonId(), teacher.getFirstName(), teacher.getLastName());
    }

    private Function<Student, StudentDto> mapStudentToStudentDtoFunction() {
        return student ->
                new StudentDto(student.getPersonId(), student.getFirstName(), student.getLastName());
    }

    @Override
    public List<TeacherDto> findAllTeachersByAccountEnabled() {
        return teacherRepository
                .findAllByAccount_Enabled(true)
                .stream()
                .map(mapTeacherToTeacherDtoFunction())
                .collect(Collectors.toList());

    }


    /* creating new course without assigning students to course*/
    @Override
    public void saveCourseDto(CourseDtoForSaving courseDtoForSaving) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(courseDtoForSaving.getTeacherId());
        Course course = new Course(courseDtoForSaving.getCourseTitle(),courseDtoForSaving.getStartDateJalali()
        ,courseDtoForSaving.getFinishDateJalali(),null,optionalTeacher.get());
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


}
