package com.example.cloproject.employee.service;

import com.example.cloproject.employee.entity.Employee;
import com.example.cloproject.employee.entity.dto.EmployeeCreateDto;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class CsvConvertor implements FileTypeConvertor {

    private static final String CSV_TYPE = "text/csv";
    private final CsvMapper csvMapper;


    @Override
    public boolean isSupportedFormat(String contentType) {
        return CSV_TYPE.equals(contentType);
    }

    @Override
    public List<EmployeeCreateDto> convert(MultipartFile file) throws IOException {
        CsvSchema schema = csvMapper.schemaFor(Employee.class).withHeader().withColumnReordering(true);
        MappingIterator<EmployeeCreateDto> iterator = csvMapper.readerWithSchemaFor(EmployeeCreateDto.class).with(schema).readValues(file.getInputStream());
        return iterator.readAll();
    }
}
