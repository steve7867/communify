# 🌏 Communify

- 다양한 주제에 대한 글을 공유하는 커뮤니티 서비스
- 대용량 트래픽을 가정하여 성능에 초점을 두고 개발 진행

## 🎯 Technical Issue

- Redis 캐싱 적용
  - Look Aside 전략을 활용한 대규모 조회 요청 처리
    - 최신 작성글, 인기 게시글 캐싱
  - Write Back 전략을 활용한 조회 수 집계
    - MyBatis Batch Insert
    - Redis Pipelining을 사용해 RTT Latency와 Socket I/O 지연 경감
  - Redis Sentinel 구성
- Event 기반 비동기 처리
- MySQL Replication을 통해 데이터베이스 부하 분산
- Firbase Cloud Messaging을 사용한 알림 기능
- AWS S3 + CloudFront를 사용한 파일 업로드, 조회

## 🤔 TroubleShooting & 기술적 고민

[🔗링크](https://github.com/steve7867/Communify/wiki/%F0%9F%A4%94-TroubleShooting-%26-%EA%B8%B0%EC%88%A0%EC%A0%81-%EA%B3%A0%EB%AF%BC)

## 🏛️ 프로젝트 구조도

![프로젝트 구조](https://github.com/user-attachments/assets/255dc9d8-859a-4d06-bcbd-c1e052bde9f9)

## 📈 ERD

![ERD](https://github.com/user-attachments/assets/61dee558-f1cf-4691-8302-e24bb75a96f9)

## 🖼️ Application UI

![Application UI](https://github.com/user-attachments/assets/67935549-00a2-482f-8f2c-3a3aec75032c)

