package com.example.cloproject.employee;

import com.example.cloproject.employee.entity.dto.EmployeeCreateDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestHelper {

    public static final String CSV_BODY =
            "김철수, charles@clovf.com,1075312468,2018.03.07\n" +
            "박영희, matilda@clovf.com,1087654321,2021.04.28\n" +
            "홍길동, kildong.hong@clovf.com,1012345678,2015.08.15\n";

    public static final String CSV_BODY_HEADER =
            "name, email, tel, joined\n" +
            "김철수, charles@clovf.com,1075312468,2018.03.07\n" +
            "박영희, matilda@clovf.com,1087654321,2021.04.28\n" +
            "홍길동, kildong.hong@clovf.com,1012345678,2015.08.15\n";

    public static final List<EmployeeCreateDto> CREATE_DTO_LIST = List.of(
        new EmployeeCreateDto("황정현", "jhh@clovf.com", "1022540970", LocalDate.of(2023, 6, 1)),
        new EmployeeCreateDto("테스트", "test@clovf.com", "1083445555", LocalDate.of(2023, 6, 1))
    );

    // 50 employee test data insert
    public static List<EmployeeCreateDto> createMockEmployees() {
        List<EmployeeCreateDto> createDtos = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            createDtos.add(new EmployeeCreateDto("테스트" + i, "test@clovf.com", "1083445555", LocalDate.of(1970 + i, 6, 1)));
        }

        return createDtos;
    }
}
