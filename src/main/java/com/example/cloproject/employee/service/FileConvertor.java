package com.example.cloproject.employee.service;

import com.example.cloproject.employee.entity.dto.EmployeeCreateDto;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class FileConvertor implements FileTypeConvertor {
    @Override
    public boolean isSupportedFormat(String contentType) {
        return MediaType.MULTIPART_FORM_DATA_VALUE.equals(contentType);
    }

    @Override
    public List<EmployeeCreateDto> convert(MultipartFile file) throws IOException {
        return null;
    }
}
