package com.example.cloproject.employee;

import com.example.cloproject.common.exception.FileEmptyException;
import com.example.cloproject.common.exception.NotSupportedFileTypeException;
import com.example.cloproject.employee.entity.Employee;
import com.example.cloproject.employee.entity.dto.EmployeeResponseDto;
import com.example.cloproject.employee.repository.EmployeeRepository;
import com.example.cloproject.employee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setup() {
        employeeService.addEmployeesFromRequestBody(TestHelper.createMockEmployees());
    }

    @Test
    @DisplayName("csv 파일 업로드시 성공 테스트")
    void addEmployeesFromCsvFileTest() throws IOException {
        final ClassPathResource resource = new ClassPathResource("sample.csv");
        MockMultipartFile file = new MockMultipartFile("test.csv", "test", "text/csv", resource.getInputStream());

        employeeService.addEmployeesFromFile(file);

        assertThat(employeeRepository.findAll().size()).isEqualTo(53);
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

        assertThat(employeeRepository.findAll().size()).isEqualTo(53);
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
        assertThat(employeeRepository.findAll().size()).isEqualTo(53);
    }

    @Test
    @DisplayName("Content-type이 'application/json' 일 경우 정보 저장")
    void addEmployeesFromRequestBody() {
        employeeService.addEmployeesFromRequestBody(TestHelper.CREATE_DTO_LIST);

        assertThat(employeeRepository.findAll().size()).isEqualTo(52);
    }

    @ParameterizedTest
    @MethodSource("getPage")
    @DisplayName("직원들의 기본 연락 정보 조회")
    void getEmployees(int page, int result) {
        final List<EmployeeResponseDto> employees = employeeService.getEmployees(0, page);

        assertThat(employees.size()).isEqualTo(result);
    }

    static Stream<Arguments> getPage() {
        return Stream.of(
                Arguments.of(10, 10),
                Arguments.of(20, 20),
                Arguments.of(30, 30),
                Arguments.of(40, 40),
                Arguments.of(50, 50)
        );
    }


    @ParameterizedTest
    @MethodSource("getUserName")
    @DisplayName("직원 기본 연락 정보 조회")
    void getEmployee(String name) {
        final List<EmployeeResponseDto> findEmployee = employeeService.getEmployee(name);
        final List<Employee> dbEmployee = employeeRepository.findByName(name);

        assertThat(findEmployee.get(0).getName()).isEqualTo(dbEmployee.get(0).getName());
        assertThat(findEmployee.get(0).getEmail()).isEqualTo(dbEmployee.get(0).getEmail());
        assertThat(findEmployee.get(0).getId()).isEqualTo(dbEmployee.get(0).getId());
        assertThat(findEmployee.get(0).getTel()).isEqualTo(dbEmployee.get(0).getTel());
    }

    static Stream<Arguments> getUserName() {
        return Stream.of(
                Arguments.of("테스트1"),
                Arguments.of("테스트2"),
                Arguments.of("테스트30"),
                Arguments.of("테스트40")
        );
    }


    @Test
    @DisplayName("존재하지 않은 회원 조회시 리스트 0")
    void selectNotExistEmployeeException() {
        final List<EmployeeResponseDto> none = employeeService.getEmployee("NONE");
        assertThat(none.size()).isEqualTo(0);
    }
}
