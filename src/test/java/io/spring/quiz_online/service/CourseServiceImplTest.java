package io.spring.quiz_online.service;

import com.ibm.icu.text.SimpleDateFormat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CourseServiceImplTest {

    @Autowired
    CourseService courseService;
    @Autowired
    SimpleDateFormat simpleDateFormat;

 /*   @Test
    public void parseDate(){
        Date date = courseService.parseDate("1399/03/05");
        System.out.println(date);
    }*/

}