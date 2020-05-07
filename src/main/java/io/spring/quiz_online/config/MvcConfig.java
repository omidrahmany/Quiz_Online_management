package io.spring.quiz_online.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {



    public void addViewControllers(ViewControllerRegistry registry) {



        /*
        registry.addViewController("/registration").setViewName("registration/registration.html");
        registry.addViewController("/login").setViewName("login/login.html");
        registry.addViewController("/").setViewName("login/login.html");
        registry.addViewController("/hello").setViewName("hello.html");
        registry.addViewController("/book").setViewName("book/book-list.html");
        registry.addViewController("/home").setViewName("home.html");
*/
    }

}