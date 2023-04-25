package com.example.cloproject.employee.service.convertor;

import com.example.cloproject.common.exception.NotSupportedFileTypeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.cloproject.common.response.ErrorCode.PARAM_INVALID_ERROR_E001;

@Component
public class FileComposite {

    private List<FileTypeConvertor> fileTypeConvertors;
    private final CsvMapper csvMapper;
    private final ObjectMapper objectMapper;

    public FileComposite(CsvMapper csvMapper, ObjectMapper objectMapper) {
        this.fileTypeConvertors = List.of(new CsvConvertor(csvMapper), new JsonConvertor(objectMapper));
        this.csvMapper = csvMapper;
        this.objectMapper = objectMapper;
    }

    public FileTypeConvertor isSupportedFile(MultipartFile file) {
        for (FileTypeConvertor typeConvertor : fileTypeConvertors) {
            if (typeConvertor.isSupportedFormat(file.getContentType())) {
                return typeConvertor;
            }
        }

        throw new NotSupportedFileTypeException(PARAM_INVALID_ERROR_E001);
    }
}
