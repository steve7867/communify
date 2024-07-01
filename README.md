# 🌏 Communify

- 다양한 주제에 대한 글을 공유하는 커뮤니티 사이트
- 대용량 트래픽을 가정하여 성능에 초점을 두고 개발 진행

## 🎯 Technical Issue

- Redis 캐시를 활용한 읽기 성능 향상
- Redis 캐시, 세션 저장소 분리
- Write Behind 전략을 활용한 좋아요, 조회 수 집계 구현
- Redis Transaction을 사용해 Redis에 다수 접근하는 로직을 atomic하게 구현
- DB 이중화(MySQL Replication)를 통한 DB 부하 분산
- DB 인덱스 설정을 통한 조회 성능 향상
- Bulk Insert를 이용한 삽입 성능 향상
- 무한 스크롤 방식의 페이징
- FireBase를 이용한 푸시 알림
- 비동기 방식을 적용해 푸시 알림 성능 향상
- Spring AOP를 사용한 로그인 체크
- Nginx의 Reversed-Proxy를 이용한 로드밸런싱
- Jasypt를 이용한 설정 정보 암호화

## 🤔 TroubleShooting & 기술적 고민

- [🔗링크](https://github.com/steve7867/Communify/wiki/%F0%9F%A4%94-TroubleShooting-&-%EA%B8%B0%EC%88%A0%EC%A0%81-%EA%B3%A0%EB%AF%BC)

## 🖼️ Application UI

![Application UI](https://github.com/steve7867/Communify/assets/115217247/d07f70ec-d9d6-424b-a4c2-e642b4dcc2e5)

## 🛠️ 사용한 기술 및 개발 환경

![사용한 기술](https://github.com/steve7867/Communify/assets/115217247/71878398-22b2-4abe-a264-678502750af1)

## 📈 ERD

![ERD](https://github.com/steve7867/Communify/assets/115217247/d06404d1-d99a-4c3f-b624-5e3f876f6a71)

## 🏛️ 프로젝트 구조도

![프로젝트 구조](https://github.com/steve7867/Communify/assets/115217247/aeaccf2d-73eb-49d5-89da-47862b9eb6b3)

## ⚙️ 주요 기능

### 🙋🏻‍♂️ 회원

- 이메일 인증을 통한 가입
- 비밀번호 인증을 통한 탈퇴
- 비밀번호 변경
- 로그인, 로그아웃
- 회원 조회
- 다른 회원 팔로우 / 언팔로우

### 📝 게시글

- 게시글 작성
- 카테고리별 게시글 목록 조회
- 인기 게시글 조회
- 게시글 상세 조회
- 게시글 수정
- 게시글 삭제
- 게시글 좋아요

### 💬 댓글

- 댓글 작성
- 게시글별 댓글 조회
- 댓글 수정
- 댓글 삭제

### 🔔 푸시 알림

- 다른 회원이 자신을 팔로우한 경우
- 팔로우 중인 회원이 새로운 게시글을 작성한 경우
- 다른 회원이 자신의 게시글에 댓글 작성한 경우
- 다른 회원이 자신의 게시글에 좋아요 버튼을 누른 경우
