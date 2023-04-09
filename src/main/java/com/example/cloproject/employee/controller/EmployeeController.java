package com.example.cloproject.employee;

import com.example.cloproject.employee.dto.EmployeeCreateDto;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/csv")
    public ResponseEntity<String> addEmployeesFromCsv(MultipartFile file) throws IOException {
        employeeService.addEmployeesFromFile(file, false);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @PostMapping("/json")
    public ResponseEntity<String> addEmployeesFromJson(@RequestParam("file") MultipartFile file) throws IOException {
        employeeService.addEmployeesFromFile(file, true);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @PostMapping("/csv-body")
    public ResponseEntity<String> addEmployeesFromCsvBody(@RequestBody String body) throws IOException {
        employeeService.addEmployeesFromCsvRequestBody(body);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @PostMapping("/json-body")
    public ResponseEntity<String> addEmployeesFromJsonBody(@RequestBody List<EmployeeCreateDto> employeeDtos) {
        employeeService.addEmployeesFromRequestBody(employeeDtos);
        return ResponseEntity.ok("File uploaded successfully");
    }
}