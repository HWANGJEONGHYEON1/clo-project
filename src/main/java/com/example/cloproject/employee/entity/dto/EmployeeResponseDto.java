package com.example.cloproject.employee.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class EmployeeResponseDto {
    private Long id;
    private String name;
    private String email;
    private String tel;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate joinDate;
}
