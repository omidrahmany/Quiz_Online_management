package io.spring.quiz_online;

import io.spring.quiz_online.config.BasicSettingOnDB;
import io.spring.quiz_online.repositories.AccountRepository;
import io.spring.quiz_online.repositories.PersonRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class QuizOnlineApplication {
    private static AccountRepository accountRepository;
    private static PersonRepository personRepository;

    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(QuizOnlineApplication.class, args);
        BasicSettingOnDB basicSettingOnDB = (BasicSettingOnDB) run.getBean("basicSettingOnDB");
        basicSettingOnDB.insertRolesIntoDB().insertManagerAccountIntoDB();



    }

}
