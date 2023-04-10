package com.example.cloproject.employee.service;

import com.example.cloproject.common.exception.FileEmptyException;
import com.example.cloproject.common.exception.NotSupportedFileTypeException;
import com.example.cloproject.employee.entity.Employee;
import com.example.cloproject.employee.entity.dto.EmployeeCreateDto;
import com.example.cloproject.employee.mapper.EmployeeMapper;
import com.example.cloproject.employee.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final CsvMapper csvMapper;
    private List<FileTypeConvertor> fileTypeConvertors;

    public EmployeeService(EmployeeRepository employeeRepository, CsvMapper csvMapper, ObjectMapper objectMapper) {
        this.employeeRepository = employeeRepository;
        this.csvMapper = csvMapper;
        this.fileTypeConvertors = List.of(new CsvConvertor(csvMapper), new JsonConvertor(objectMapper));
    }

    public void addEmployeesFromFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new FileEmptyException("File is empty");
        }

        List<Employee> employees = employeeDtosToEntities(getEmployeeDtos(file));

        employeeRepository.saveAll(employees);
    }

    private List<EmployeeCreateDto> getEmployeeDtos(MultipartFile file) throws IOException {
        for (FileTypeConvertor fileTypeConvertor : fileTypeConvertors) {
            if (fileTypeConvertor.isSupportedFormat(file.getContentType())) {
                return fileTypeConvertor.convert(file);
            }
        }

        throw new NotSupportedFileTypeException("File format is not supported");
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
