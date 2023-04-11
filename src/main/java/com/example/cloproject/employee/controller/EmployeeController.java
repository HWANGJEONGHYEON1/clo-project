package com.example.cloproject.employee.controller;

import com.example.cloproject.employee.service.EmployeeService;
import com.example.cloproject.employee.entity.dto.EmployeeCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> addEmployeesFromJson(@RequestBody List<EmployeeCreateDto> body) throws IOException {
        employeeService.addEmployeesFromRequestBody(body);
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.CREATED);
    }

    @PostMapping(consumes = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> addEmployeesFromCsv(@RequestBody String body) throws IOException {
        employeeService.addEmployeesFromCsvRequestBody(body);
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.CREATED);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addEmployees(MultipartFile file) throws IOException {
        employeeService.addEmployeesFromFile(file);
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.CREATED);
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> addEmployeesFromFormData(String a) throws IOException {
        employeeService.addEmployeesFromFormData(a);
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.CREATED);
    }
}