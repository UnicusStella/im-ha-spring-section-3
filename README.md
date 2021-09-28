# Section 3 Hiring Assessment

이번 Section 3 Hiring Assessment에서는 로그인 여부에 따른 마이페이지를 보여주는 fullstack web application을 만들어야 합니다.

## Getting Started

1. 서버와 클라이언트 모두 구현해야 합니다.
    - 서버: `ha-3-server`
    - 클라이언트: `ha-section-3-client`
2. `npm install`을 이용해 클라이언트의 의존성 모듈(dependencies)를 설치할 수 있습니다.
3. `npm test`를 통해 클라이언트 및 서버의 테스트를 진행할 수 있습니다.
4. `application.properties`에 DB password 설정을 해야합니다. 
5. `ha_section_3` 데이터 베이스를 MySQL에 `create database ha_section_3` SQL을 작성하여 직접 생성 해야합니다. 

## 서버

- `Ha3ServerApplication.java` 파일을 통해 서버를 실행시킬 수 있습니다.

### Requirements

주어진 모든 테스트를 통과하세요. 다음과 같은 REST API를 구현해야 합니다.

| 메소드 | endpoint   | 설명                                                                     |
| ------ | ---------- | ------------------------------------------------------------------------ |
| POST   | `/signup`  | 회원 가입 요청 (모든 필드 필요)                                          |
| POST   | `/signin`  | 로그인 요청 (`email`, `password` 필드 필요)                              |
| POST   | `/signout` | 로그아웃 요청                                                            |
| GET    | `/auth`    | 사용자 정보 조회, 인증에 따라 권한이 부여되며, 본인의 정보만 조회 가능함 |

- https 프로토콜을 사용해야 합니다.(resources 폴더에 추가해야합니다.)
  - 인증서 발급은 mkcert 프로그램을 이용합니다.
- 회원가입 되어있는 사용자만 로그인을 할 수 있어야 합니다.
- 쿠키에 담긴 토큰은 인증을 판단하는 근거가 됩니다.
  - JWT를 이용합니다.
- 사용자 정보 조회 api는 권한을 판단하는 근거가 됩니다.
- 회원가입 시 사용자 정보는 데이터베이스에 기록되어야 합니다.
- 사용자 정보 조회시 데이터베이스에서 정보를 읽어서 응답해야 합니다.

데이터베이스는 다음과 같은 조건을 충족해야 합니다.

1. 유저정보를 데이터베이스에 저장할 `ServiceUser` Entity가 존재해야 합니다.
2. `ServiceUser` 모델에는 `id`, `email`, `password`, `username`, `mobile`, `createdAt`, `updatedAt` 필드들이 포함되어야 합니다.
3. `LoginController`, `LoginService`, `LoginRepository` 안에 내용을 작성합니다. 

## 클라이언트

- `npm start`로 리액트 개발 서버를 실행 합니다.
- `npm test`로 테스트를 실행할 수 있습니다.

### Requirements

주어진 모든 테스트를 통과하세요.

1. 로그인이 되어있는 사용자만 메인 페이지로 접속 할 수 있어야 합니다.
2. 로그인이 되어 있지 않은 사용자는 로그인 페이지로 보냅니다.
3. 로그인 요청을 서버에 보낼 수 있어야 합니다.
    - 로그인 요청을 보낼 때에, 이메일과 비밀번호를 입력하지 않을 경우 에러 처리가 필요합니다.
4. 로그인을 한 사용자만 마이페이지에서 개인 정보를 열람할 수 있어야 합니다.
5. 회원가입 페이지에서는 회원 가입 요청을 서버에 보낼 수 있어야 합니다.
    - 모든 항목을 입력하지 않을 경우 에러 처리가 필요합니다.
6. 마이페이지에는 로그아웃 버튼이 존재하며, 클릭시 로그아웃 요청을 보내야 합니다.

## 제출

- (client) 먼저 `npm test`로 각각의 테스트가 실행하는지 확인하세요. 테스트가 실행되지 않는 경우 (문법 에러, 데이터베이스 연결에 실패 등) 제출할 수 없습니다.
- (client) 테스트가 실행되는 것을 확인하고, 테스트를 통과한 후 `npm run submit`을 이용해 과제를 제출해주세요.
- (server) `HaTest.java` 파일을 통해서 테스트와 제출을 수행합니다.
- 서버, 클라이언트 각각 따로 제출해야 합니다.


# im-ha-spring-section-3
