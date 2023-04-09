package com.example.cloproject.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    /*
    역직렬화 할 때 LocalDate 값을 제대로 읽지 못하여 추가
     */
    private final JavaTimeModule javaTimeModule = new JavaTimeModule();

    @Bean
    public CsvMapper csvMapper() {
        final CsvMapper csvMapper = new CsvMapper();
        csvMapper.registerModule(javaTimeModule);
        return csvMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }
}
