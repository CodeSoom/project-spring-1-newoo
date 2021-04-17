# Assemble

## Assemble은 어떤 어플리케이션일까요?

취미나 관심사과 비슷한 사용자들끼리 모일 수 있는 서비스를 제공하는 어플리케이션입니다.

사용자들은 원하는 모임을 검색하고 참가할 수 있으며, 새로운 모임을 만들고 관리할 수 있습니다.

## 어떻게 서비스를 이용할 수 있나요?
UI를 통한 서비스는 iOS 앱 > Web > Android 순서대로 프론트엔드가 개발된 후에 이용가능 예정입니다.
UI 없이는 Postman이나 Insomnia와 같은 API Client 프로그램을 활용하여 테스트 가능합니다.

(해당 레포지토리에는 서버사이드 소스코드만 존재합니다.)

(추후, 링크를 통해 프론트엔드 레포지토리를 추가할 예정입니다.)

## 상세 기능

### 모임 기능 API
- 모임 리스트 조회
  - `GET /meetings`
- 모임 상세 조회
  - `GET /meetings/{id}`
- 모임 생성
  - `POST /meetings`
- 모임 수정
  - `PATCH /meetings/{id}`
- 모임 삭제
  - `DELETE /meetings/{id}`

### 회원정보 기능 API
- 회원가입
  - `POST /users`
- 회원정보 수정
  - `PATCH /users/{id}`
- 회원탈퇴
  - `DELETE /users/{id}`

### 세션 API
- 로그인
  - `POST /session`
