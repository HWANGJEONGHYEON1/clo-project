package com.example.cloproject.employee.controller;

import com.example.cloproject.employee.service.EmployeeService;
import com.example.cloproject.employee.entity.dto.EmployeeCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @RequestMapping
    public ResponseEntity<String> addEmployees(MultipartFile file, @RequestBody String body) throws IOException {
        employeeService.addEmployeesFromFile(file);
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.CREATED);
    }

    @PostMapping(consumes = "text/csv")
    public ResponseEntity<String> addEmployeesFromCsvBody(@RequestBody String body) throws IOException {
        employeeService.addEmployeesFromCsvRequestBody(body);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addEmployeesFromJsonBody(@RequestBody List<EmployeeCreateDto> employeeDtos) {
        employeeService.addEmployeesFromRequestBody(employeeDtos);
        return ResponseEntity.ok("File uploaded successfully");
    }
}