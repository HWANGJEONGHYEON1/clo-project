package com.example.cloproject.employee.controller;

import com.example.cloproject.employee.entity.dto.EmployeeResponseDto;
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
@RequestMapping("/api/employee")
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

    @GetMapping
    public List<EmployeeResponseDto> employeeResponseDtos(@RequestParam(required = false, defaultValue = "0") int page,
                                                          @RequestParam(required = false, defaultValue = "20") int pageSize) {
        return employeeService.getEmployees(page, pageSize);
    }
}