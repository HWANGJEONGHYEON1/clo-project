package com.example.cloproject.employee.controller;

import com.example.cloproject.common.response.ResponseObject;
import com.example.cloproject.employee.entity.dto.EmployeeResponseDto;
import com.example.cloproject.employee.service.EmployeeService;
import com.example.cloproject.employee.entity.dto.EmployeeCreateDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> addEmployeesFromJson(@RequestBody List<EmployeeCreateDto> body) throws IOException {
        employeeService.addEmployeesFromRequestBody(body);
        return new ResponseEntity<>("JSON Type registration successfully", getHttpHeaders(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "파일 및 requestBody 등록/입력 시 직원 저장", notes = "직원을 디비에 저장합니다.", response = String.class)
    @PostMapping(consumes = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> addEmployeesFromCsv(@RequestBody String body) throws IOException {
        employeeService.addEmployeesFromCsvRequestBody(body);
        return new ResponseEntity<>("CSV Type registration successfully", getHttpHeaders(), HttpStatus.CREATED);
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addEmployees(MultipartFile file) throws IOException {
        employeeService.addEmployeesFromFile(file);
        return new ResponseEntity<>("File uploaded successfully", getHttpHeaders(), HttpStatus.CREATED);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("http://localhost:8080/api/employee"));
        return httpHeaders;
    }

    @ApiOperation(value = "직원 리스트 조회", notes = "직원들을 조회합니다.", response = List.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "페이지 번호", dataTypeClass = int.class),
            @ApiImplicitParam(name = "perPage", value = "페이지 당 갯수", dataTypeClass = int.class)
    })
    @GetMapping
    public List<EmployeeResponseDto> employeeResponseDtos(@RequestParam(required = false, defaultValue = "0") int page,
                                                          @RequestParam(required = false, defaultValue = "20") int pageSize) {
        return employeeService.getEmployees(page, pageSize);
    }

    @ApiOperation(value = "직원 이름으로 조회", notes = "직원 조회합니다.", response = List.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "직원이름", dataTypeClass = String.class)
    })
    @GetMapping("/{name}")
    public List<EmployeeResponseDto> employeeResponseDto(@PathVariable String name) {
        return employeeService.getEmployee(name);
    }
}