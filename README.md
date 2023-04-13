# Clo project


## 패키지 설명
- common: 에러처리 및 전체적인 공통 기능(로그, 응답)
- config: 스프링 빈 및 스웨거 관련 설정
- employee
  - controller: Content-type 별 파일 및 RequestBody 처리
  - service: 비지니스 로직 담당
  - repository: JPA를 이용한 쿼리 수행
  - mapper: mapstruct를 이용한 객체 매핑
  - entity: 테이블 매핑 객체
- test: 서비스 기능 테스트

## 기능 설명

- / 로 접근 시 직원 등록할 수 있는 화면
  - 파일등록은 CSV, JSON만 등록 가능
    - CSV 형식은 헤더 필수 
  - RequestBody는 Json, CSV 형식만 지원
    - CSV 형식에서는 헤더 제외 필수
- 직원등록이 성공된다면 JSON 형태의 화면으로 이동
- 직원 등록 실패 시 에러 메시지
- 데이터 저장은 in-memory 형식이라서 서비스 중단 후 재실행 시 데이터 유실



## API 문서 (서비스 구동 시)
- http://localhost:8080/swagger-ui/index.html

## 실행방법
- git clone https://github.com/HWANGJEONGHYEON1/clo-project.git
- cd clo-project
- gradle build test 
- java -jar build/libs/clo-project-0.0.1-SNAPSHOT.jar
- http://localhost:8080 접속
  - 파일 업로드 (sample 파일은 test/java/resources에 두개 있습니다.)
  - RequestBody 형식 등록