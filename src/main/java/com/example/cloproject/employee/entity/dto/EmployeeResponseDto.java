package com.example.cloproject.employee.entity.dto;

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
    private LocalDate joinDate;
}
