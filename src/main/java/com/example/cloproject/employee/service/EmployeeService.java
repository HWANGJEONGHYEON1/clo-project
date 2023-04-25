package com.example.cloproject.employee.service;

import com.example.cloproject.common.exception.FileEmptyException;
import com.example.cloproject.common.exception.NotSupportedFileTypeException;
import com.example.cloproject.employee.entity.Employee;
import com.example.cloproject.employee.entity.dto.EmployeeCreateDto;
import com.example.cloproject.employee.entity.dto.EmployeeResponseDto;
import com.example.cloproject.employee.mapper.EmployeeMapper;
import com.example.cloproject.employee.repository.EmployeeRepository;
import com.example.cloproject.employee.service.convertor.CsvConvertor;
import com.example.cloproject.employee.service.convertor.FileComposite;
import com.example.cloproject.employee.service.convertor.FileTypeConvertor;
import com.example.cloproject.employee.service.convertor.JsonConvertor;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.cloproject.common.response.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final CsvMapper csvMapper;
    private final FileComposite fileComposite;


    public void addEmployeesFromFile(MultipartFile file) throws IOException {
        if (file == null) {
            throw new FileEmptyException(PARAM_INVALID_ERROR_E003);
        }

        List<Employee> employees = employeeDtosToEntities(getEmployeeDtos(file));

        employeeRepository.saveAll(employees);
    }

    private List<EmployeeCreateDto> getEmployeeDtos(MultipartFile file) throws IOException {
        FileTypeConvertor supportedFile = fileComposite.isSupportedFile(file);
        return supportedFile.convert(file);
    }

    public void addEmployeesFromCsvRequestBody(String body) throws IOException {
        StringBuilder csvBody = new StringBuilder();
        csvBody.append("name, email, tel, joined\n");
        csvBody.append(body);
        CsvSchema csvSchema = csvMapper.schemaFor(EmployeeCreateDto.class).withHeader().withColumnReordering(true);
        MappingIterator<EmployeeCreateDto> it = csvMapper.readerFor(EmployeeCreateDto.class).with(csvSchema).readValues(csvBody.toString());
        final List<Employee> employees = employeeDtosToEntities(it.readAll());
        employeeRepository.saveAll(employees);
    }

    public void addEmployeesFromRequestBody(List<EmployeeCreateDto> employeeDtos) {
        final List<Employee> employees = employeeDtosToEntities(employeeDtos);
        employeeRepository.saveAll(employees);
    }

    private List<Employee> employeeDtosToEntities(List<EmployeeCreateDto> createDtos) {
        List<Employee> employees = new ArrayList<>();
        for (EmployeeCreateDto dto : createDtos) {
            employees.add(EmployeeMapper.INSTANCE.toEntity(dto));
        }

        return employees;
    }

    public List<EmployeeResponseDto> getEmployees(int page, int pageSize) {
        return employeeRepository.findAll(PageRequest.of(page, pageSize))
                .toList()
                .stream()
                .map(EmployeeMapper.INSTANCE::toResponseDto)
                .collect(Collectors.toList());
    }

    // 동명이인일 경우 리스트로 보여준다.
    public List<EmployeeResponseDto> getEmployee(String name) {
        return employeeRepository.findByName(name)
                .stream()
                .map(EmployeeMapper.INSTANCE::toResponseDto)
                .collect(Collectors.toList());
    }
}
