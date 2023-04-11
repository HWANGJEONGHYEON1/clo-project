package com.example.cloproject.employee.entity.dto;

import com.example.cloproject.config.LocalDateDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class EmployeeCreateDto {

    @NotBlank
    @JsonProperty(value = "name")
    private String name;

    @NotBlank
    @JsonProperty(value = "email")
    private String email;

    @NotBlank
    @JsonProperty(value = "tel")
    private String tel;

    @NotBlank
    @JsonProperty(value = "joined")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate joinDate;
}
