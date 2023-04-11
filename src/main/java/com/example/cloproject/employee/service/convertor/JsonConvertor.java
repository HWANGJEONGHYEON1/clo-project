package com.example.cloproject.employee.service.convertor;

import com.example.cloproject.employee.entity.dto.EmployeeCreateDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JsonConvertor implements FileTypeConvertor {

    private final ObjectMapper objectMapper;

    @Override
    public boolean isSupportedFormat(String contentType) {
        return MediaType.APPLICATION_JSON_VALUE.equals(contentType);
    }

    @Override
    public List<EmployeeCreateDto> convert(MultipartFile file) throws IOException {
        return objectMapper.readValue(file.getInputStream(), new TypeReference<>(){});
    }
}
