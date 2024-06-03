# 🌏 Communify

- 커뮤니티 사이트를 개발하는 프로젝트
- 대용량 트래픽을 가정하여 성능에 초점을 두고 개발 진행

## 🎯 Technical Issue

- Redis 캐시를 활용한 읽기 작업 성능 향상
- Redis 캐시 저장소, 세션 저장소 분리
- Write Behind 전략을 활용해 게시글 좋아요, 조회 수 집계 구현
- Redis Transaction을 사용해 Redis에 다수 접근하는 기능을 atomic하게 구현
- DB 이중화(MySQL Replication)를 통한 데이터베이스 부하 분산
- DB 인덱스 설정을 통해 조회 성능 향상
- Bulk Insert를 이용한 데이터 삽입 성능 향상
- 무한 스크롤 방식의 페이징 처리를 통해 게시글 목록 조회 성능 향상
- FireBase를 이용한 푸시 알림 기능
- 비동기 방식을 적용해 푸시 알림 기능 향상
- Spring AOP를 사용한 로그인 체크 기능
- Nginx의 Reversed-Proxy를 이용하여 로드밸런싱
- Jasypt를 이용해 설정 정보 암호화

## 🤔 TroubleShooting & 기술적 고민

- [🔗링크](https://github.com/steve7867/Communify/wiki/TroubleShooting-&-%EA%B8%B0%EC%88%A0%EC%A0%81-%EA%B3%A0%EB%AF%BC)

## 🖼️ Application UI

![Application UI](https://github-production-user-asset-6210df.s3.amazonaws.com/115217247/335965817-935a8024-04d5-4c45-8f0d-79c8c6fc038f.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20240603%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20240603T065219Z&X-Amz-Expires=300&X-Amz-Signature=3a27b21fa2cdf7ac8c1ded8a34f1fcd085d32dbd7ccfc2367b39b3e5ea305927&X-Amz-SignedHeaders=host&actor_id=115217247&key_id=0&repo_id=809634469)

## 🛠️ 사용한 기술 및 개발 환경

![사용한 기술](https://github-production-user-asset-6210df.s3.amazonaws.com/115217247/335965880-b7abcaee-0ac6-44e5-8ebf-836bc08ddc58.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20240603%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20240603T065244Z&X-Amz-Expires=300&X-Amz-Signature=3e2068cfb46d712f042b275f0b85d039b2d92a81738968ba851395dcb31a2fdd&X-Amz-SignedHeaders=host&actor_id=115217247&key_id=0&repo_id=809634469)

## 📈 ERD

![ERD](https://github-production-user-asset-6210df.s3.amazonaws.com/115217247/335965879-9954c9e9-1514-4090-94da-784c33587bbd.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20240603%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20240603T065236Z&X-Amz-Expires=300&X-Amz-Signature=ce66b80bc81e79526bc89fea9e36664e5ce2cea21ebc28c52551fb15dc94b897&X-Amz-SignedHeaders=host&actor_id=115217247&key_id=0&repo_id=809634469)

## 🏛️ 프로젝트 구조도

![프로젝트 구조](https://github-production-user-asset-6210df.s3.amazonaws.com/115217247/335965869-dae47b03-cf5b-4911-9382-3c667b3d96db.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20240603%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20240603T065227Z&X-Amz-Expires=300&X-Amz-Signature=b19b43f58e08f99f400bb6a8fab34b9d29b6a4c0f04250c91ad302af24c13469&X-Amz-SignedHeaders=host&actor_id=115217247&key_id=0&repo_id=809634469)

## ⚙️ 주요 기능

### 🙋🏻‍♂️ 회원

- 이메일 인증을 통한 가입
- 패스워드 인증을 통한 탈퇴
- 로그인, 로그아웃
- 회원 조회
- 다른 회원 팔로우

### 📝 게시글

- 게시글 작성
- 카테고리별 게시글 목록 조회
- 게시글 상세 조회
- 게시글 수정
- 게시글 삭제
- 게시글 좋아요 및 좋아요 취소

### 💬 댓글

- 댓글 작성
- 게시글별 댓글 조회
- 댓글 수정
- 댓글 삭제

### 🔔 푸시 알림

- 다른 사람이 자신을 팔로우한 경우
- 팔로우 중인 사람이 게시글을 작성한 경우
- 다른 사람이 자신의 게시글에 댓글 작성한 경우
- 다른 사람이 자신의 게시글에 좋아요 버튼을 누른 경우
