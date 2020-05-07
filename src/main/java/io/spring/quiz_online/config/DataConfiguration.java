package io.spring.quiz_online.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "io.spring.quiz_online.repositories")
public class DataConfiguration {
}


