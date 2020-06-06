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

    public CourseServiceImpl(TeacherRepository teacherRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
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
                new StudentDto(student.getPersonId(), student.getFirstName(), student.getLastName(), student.getAccount().getEmail());
    }

    @Override
    public List<TeacherDto> findAllTeachersByAccountEnabled() {
        return teacherRepository
                .findAllByAccount_Enabled(true)
                .stream()
                .map(mapTeacherToTeacherDtoFunction())
                .collect(Collectors.toList());

    }


    /* creating new course */
    @Override
    public void saveCourseDto(CourseDtoForSaving courseDtoForSaving) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(courseDtoForSaving.getTeacherId());

        List<Student> students = new ArrayList<>();
        if (courseDtoForSaving.getSelectedStudentsEmail() != null)
            for (String email : courseDtoForSaving.getSelectedStudentsEmail())
                studentRepository.findByAccount_Email(email).ifPresent(students::add);
        Course course = null;
        if (courseDtoForSaving.getCourseId() == null)
            course = new Course(courseDtoForSaving.getCourseTitle(), courseDtoForSaving.getStartDateJalali()
                    , courseDtoForSaving.getFinishDateJalali(), students, optionalTeacher.get());
        else {
            Optional<Course> courseOptional = courseRepository.findById(courseDtoForSaving.getCourseId());
            if (courseOptional.isPresent()){
                course = courseOptional.get();
                course.setStudents(students);
                course.setTeacher(optionalTeacher.get());
                course.setCourseTitle(courseDtoForSaving.getCourseTitle());
                course.setStartDate(courseDtoForSaving.getStartDateJalali());
                course.setFinishDate(courseDtoForSaving.getFinishDateJalali());
            }
        }
        courseRepository.save(course);
    }


    @Override
    public List<StudentDto> findAllStudentsByAccountEnabled() {
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

    @Override
    public CourseDto findCourseById(Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent())
            return courseOptional.map(mapCourseToCourseDtoFunction()).get();
        else return null;
    }


}
