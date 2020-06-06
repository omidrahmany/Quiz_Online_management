package io.spring.quiz_online.service;

import io.spring.quiz_online.dto.CourseDto;
import io.spring.quiz_online.model.Course;
import io.spring.quiz_online.repositories.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class CourseServiceImplTest {

    @Autowired
    CourseService courseService;
    @Autowired
    CourseRepository courseRepository;


    @Test
    void testFindAll() {
        courseService.findAll().forEach(courseDto -> {
            System.out.println(courseDto.getCourseTitle());
            System.out.println(courseDto.getTeacherDto().getFirstName());
            System.out.println(courseDto.getStudentDtoList().size());
        });
    }

    @Test
    void testFindAll1() {
        System.out.println(courseRepository.findAll().size());
    }

    @Test
    void mapCourseToCourseDtoFunction() {
    }

    @Test
    void findCourseById() {
        CourseDto courseById = courseService.findCourseById(18L);
        courseById.getStudentDtoList()
                .forEach(studentDto -> System.out.println(studentDto.getFirstName()));
        System.out.println(courseById.getTeacherDto().getFirstName());
    }

 /*   @Test
    public void parseDate(){
        Date date = courseService.parseDate("1399/03/05");
        System.out.println(date);
    }*/

}