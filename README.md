# 🌏 Communify

- 다양한 주제에 대한 글을 공유하는 커뮤니티 서비스
- 대용량 트래픽을 가정하여 성능에 초점을 두고 개발 진행

## 🎯 Technical Issue

- Write Back 전략을 활용한 조회 수 집계
  - MyBatis Batch Insert
  - Redis Pipelining을 사용해 RTT Latency와 Socket I/O 지연 경감
- Look Aside 전략을 활용한 대용량 읽기 요청 처리
  - 최신 작성글, 인기 게시글 캐싱
- Event 기반 비동기 처리
- MySQL Replication을 통해 데이터베이스 부하 분산
- Firbase Cloud Messaging을 사용한 알림 기능
- AWS S3 + CloudFront를 사용한 파일 업로드, 조회

## 🤔 TroubleShooting & 기술적 고민

[🔗링크](https://github.com/steve7867/Communify/wiki/%F0%9F%A4%94-TroubleShooting-%26-%EA%B8%B0%EC%88%A0%EC%A0%81-%EA%B3%A0%EB%AF%BC)

## 🏛️ 프로젝트 구조도

![프로젝트 구조](https://github.com/user-attachments/assets/c3e08a08-2f7e-4955-b4b3-a859ea37ed5b)

## 📈 ERD

![ERD](https://github.com/user-attachments/assets/85b1e7fd-6ac5-469d-94f7-cd61ee3deb9b)

## 🖼️ Application UI

![Application UI](https://github.com/user-attachments/assets/67935549-00a2-482f-8f2c-3a3aec75032c)

