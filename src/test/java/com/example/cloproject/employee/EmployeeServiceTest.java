package com.example.cloproject.employee;

import com.example.cloproject.common.exception.FileEmptyException;
import com.example.cloproject.common.exception.NotSupportedFileTypeException;
import com.example.cloproject.employee.repository.EmployeeRepository;
import com.example.cloproject.employee.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("csv 파일 업로드시 성공 테스트")
    void addEmployeesFromCsvFileTest() throws IOException {
        final ClassPathResource resource = new ClassPathResource("sample.csv");
        MockMultipartFile file = new MockMultipartFile("test.csv", "test", "text/csv",resource.getInputStream());

        employeeService.addEmployeesFromFile(file);

        assertThat(employeeRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("json 파일 업로드시 성공 테스트")
    void addEmployeesFromJsonFileTest() throws IOException {
        final ClassPathResource resource = new ClassPathResource("sample.json");

        String fileName = "sample";
        String contentType = MediaType.APPLICATION_JSON_VALUE;
        byte[] content = Files.readAllBytes(resource.getFile().toPath());

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                fileName,
                fileName,
                contentType,
                content
        );
        employeeService.addEmployeesFromFile(mockMultipartFile);

        assertThat(employeeRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("올바르지 않은 파일 업로드시 에러 발생 테스트")
    void addFileException() {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test", (byte[]) null);

        assertThatThrownBy(() -> employeeService.addEmployeesFromFile(mockMultipartFile))
                .isInstanceOf(NotSupportedFileTypeException.class);
    }

    @Test
    @DisplayName("파일 없을 시 에러 발생 테스트")
    void notExistFileException() {

        assertThatThrownBy(() -> employeeService.addEmployeesFromFile(null))
                .isInstanceOf(FileEmptyException.class);
    }

    @Test
    @DisplayName("Content-type이 'text/csv' 일 경우 정보 저장")
    void addEmployeesFromCsvRequestBody() throws IOException {
        employeeService.addEmployeesFromCsvRequestBody(TestHelper.CSV_BODY);
        assertThat(employeeRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Content-type이 'application/json' 일 경우 정보 저장")
    void addEmployeesFromRequestBody() {
        employeeService.addEmployeesFromRequestBody(TestHelper.CREATE_DTO_LIST);

        assertThat(employeeRepository.findAll().size()).isEqualTo(2);
    }
}
