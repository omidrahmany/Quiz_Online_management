package io.spring.quiz_online.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeacherRepositoryTest {
@Autowired
private TeacherRepository teacherRepository;

    @Test
    void findAllTeacherEnabled(){
teacherRepository.findAllByAccount_Enabled(true).forEach(teacher -> System.out.println(teacher.getFirstName()));
    }


}