package io.spring.quiz_online.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/registration").setViewName("registration/registration.html");
        registry.addViewController("/login").setViewName("login.html");

        /*----------------------------------------- manager -----------------------------------------*/
        registry.addViewController("/manager-panel").setViewName("manager/manager-panel.html");
        registry.addViewController("/non-active-accounts").setViewName("manager/non-active-accounts.html");
        registry.addViewController("/all-accounts").setViewName("manager/all-accounts.html");


        /*----------------------------------------- student -----------------------------------------*/
        registry.addViewController("/student-profile").setViewName("student/student-profile.html");

    }
}