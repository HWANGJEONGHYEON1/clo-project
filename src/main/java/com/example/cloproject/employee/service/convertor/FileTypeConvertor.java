package com.example.cloproject.employee.service;

import com.example.cloproject.employee.entity.dto.EmployeeCreateDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileTypeConvertor {
    boolean isSupportedFormat(String contentType);
    List<EmployeeCreateDto> convert(MultipartFile file) throws IOException;
}
