package io.spring.quiz_online.config;


import com.ibm.icu.text.SimpleDateFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class DefinitionBeanConfig {

    @Bean
    public SimpleDateFormat persianDateFormat() {
        return new SimpleDateFormat("yyyy/MM/dd", new Locale("fa", "IR"));
    }
}
