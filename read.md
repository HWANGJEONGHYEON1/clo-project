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
- 직원등록이 성공된다면 JSON 형태의 화면으로 이동
- 직원 등록 실패 시 에러 메시지
- 데이터 저장은 in-memory 형식이라서 서비스 중단 후 재실행 시 데이터 유실



## API 문서 (서비스 구동 시)
- http://localhost:8080/swagger-ui/index.html