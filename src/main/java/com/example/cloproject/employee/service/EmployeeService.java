package com.example.cloproject.employee;

import com.example.cloproject.common.exception.FileEmptyException;
import com.example.cloproject.employee.dto.EmployeeCreateDto;
import com.example.cloproject.employee.mapper.EmployeeMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final CsvMapper csvMapper;
    private final ObjectMapper objectMapper;


    public void addEmployeesFromFile(MultipartFile file, boolean isJson) throws IOException {
        if (file.isEmpty()) {
            throw new FileEmptyException("File is empty");
        }

        List<Employee> employees = parsingFile(file, isJson);

        employeeRepository.saveAll(employees);
    }

    private List<Employee> parsingFile(MultipartFile file, boolean isJson) throws IOException {
        List<Employee> employees;
        if (isJson) {
            return objectMapper.readValue(file.getInputStream(), new TypeReference<>(){});
        }

        CsvSchema schema = csvMapper.schemaFor(Employee.class).withHeader().withColumnReordering(true);
        MappingIterator<Employee> iterator = csvMapper.readerWithSchemaFor(Employee.class).with(schema).readValues(file.getInputStream());
        employees = iterator.readAll();
        return employees;
    }


    public void addEmployeesFromCsvRequestBody(String body) throws IOException {
        CsvSchema csvSchema = csvMapper.schemaFor(EmployeeCreateDto.class).withHeader().withColumnReordering(true);
        MappingIterator<EmployeeCreateDto> it = csvMapper.readerFor(EmployeeCreateDto.class).with(csvSchema).readValues(body);
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
}
