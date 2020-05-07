package io.spring.quiz_online;

import io.spring.quiz_online.service.RoleHandlingInDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class QuizOnlineApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(QuizOnlineApplication.class, args);
        RoleHandlingInDB roleHandlingInDB = (RoleHandlingInDB) run.getBean("roleHandlingInDB");
        roleHandlingInDB.createRolesInDB();

    }

}
