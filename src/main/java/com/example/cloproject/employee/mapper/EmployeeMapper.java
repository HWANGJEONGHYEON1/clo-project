package com.example.cloproject.employee.mapper;

import com.example.cloproject.employee.entity.Employee;
import com.example.cloproject.employee.entity.dto.EmployeeCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee toEntity(EmployeeCreateDto dto);

    EmployeeCreateDto toResponseDto(Employee entity);
}
