package com.uff.espaco_aluno.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.configOverride(java.time.LocalDate.class)
                .setFormat(JsonFormat.Value.forPattern("dd/MM/yyyy"));

        objectMapper.configOverride(java.time.LocalDateTime.class)
                .setFormat(JsonFormat.Value.forPattern("dd/MM/yyyy HH:mm:ss"));

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return objectMapper;
    }
}

