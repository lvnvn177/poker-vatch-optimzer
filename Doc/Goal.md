
## 폴더 구조 

poker_vatch_optimzer
├── build.gradle
├── gradlew
├── gradlew.bat
├── settings.gradle
├── Doc
│   ├── PRD.md
│   └── Table.md
└── src
    └── main
        ├── java
        │   └── com
        │       └── sellanding
        │           └── poker_vatch_optimzer
        │               ├── PokerVatchOptimzerApplication.java
        │               ├── batch          # 배치 관련 패키지
        │               │   ├── job        # Spring Batch Job 설정
        │               │   │   └── RankUpdateJobConfig.java
        │               │   ├── tasklet    # 간단한 Tasklet 기반 스텝
        │               │   └── processor  # ItemProcessor 구현체
        │               ├── common         # 공통 모듈 (DTO, Exception 등)
        │               │   ├── dto
        │               │   └── exception
        │               ├── config         # 설정 파일
        │               │   ├── RedisConfig.java
        │               │   ├── SecurityConfig.java
        │               │   └── WebSocketConfig.java
        │               ├── controller     # API 엔드포인트
        │               │   ├── AuthController.java
        │               │   ├── UserController.java
        │               │   ├── GameController.java
        │               │   └── LeaderboardController.java
        │               ├── domain         # JPA Entity
        │               │   ├── User.java
        │               │   ├── GameHistory.java
        │               │   ├── RewardHistory.java
        │               │   ├── ChipTransaction.java
        │               │   └── GameSession.java
        │               ├── repository     # JPA Repository
        │               │   ├── UserRepository.java
        │               │   ├── GameHistoryRepository.java
        │               │   └── ChipTransactionRepository.java
        │               ├── service        # 비즈니스 로직
        │               │   ├── UserService.java
        │               │   ├── MatchingService.java  # 실시간 매칭 로직
        │               │   ├── ChipService.java      # 칩 트랜잭션 관리
        │               │   ├── RewardService.java    # 보상 지급 로직
        │               │   └── LeaderboardService.java # 리더보드 관리
        │               └── global         # 전역 관리
        │                   └── jwt        # JWT 토큰 관련
        │                       ├── JwtTokenProvider.java
        │                       └── TokenInfo.java
        └── resources
            ├── application.properties
            ├── static/
            └── templates/


## 순서

domain (완료): 데이터베이스와 매핑될 핵심 Entity를 정의합니다.
repository (완료): domain을 기반으로 데이터베이스에 접근하는 인터페이스를 정의합니다.
service: repository를 사용하여 실제 비즈니스 로직을 구현합니다.
config: Security, JWT 등 프로젝트 전반에 필요한 설정을 구성합니다.
controller: service를 호출하여 클라이언트와 통신하는 API 엔드포인트를 만듭니다.
batch: 등급 산정 등 스케줄링이 필요한 배치 작업을 구현합니다.



