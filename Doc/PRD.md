# PRD (Product Requirements Document)

## 프로젝트명

**온라인 포커 백엔드 시스템 (등급/배치 + 실시간 매칭/칩 관리/리더보드)**

---

## 1. 프로젝트 개요

본 프로젝트는 온라인 포커(한게임 포커 벤치마킹)를 기반으로,  
**정기 배치 시스템, 데이터 관리, 실시간 매칭, 칩 트랜잭션, 리더보드** 기능을 구현하여  
**백엔드 직무 역량(데이터 모델링, 트랜잭션, 동시성 제어, 배치 최적화, 캐싱 전략)**을 종합적으로 보여주는 것을 목표로 한다.

---

## 2. 프로젝트 목표

- **배치/데이터 관리**
    
    - 주기적 등급 갱신 및 보상 지급 자동화
        
    - 대량 데이터 환경에서 성능 최적화
        
- **인게임 기능**
    
    - 실시간 매칭 시스템 구현
        
    - 칩(가상화폐) 안전한 트랜잭션 관리
        
    - 실시간 리더보드 제공
        

---

## 3. 기능 요구사항

### 3.1 회원 관리

- 회원가입 / 로그인 (Spring Security + JWT)
    
- 보유 칩, 현재 등급, 승패 기록 조회 가능
    

### 3.2 게임 기록 관리

- 1:1 포커 미니게임 (랜덤 카드 분배, 점수 기록)
    
- `GameHistory`에 승패, 점수 저장
    

### 3.3 배치: 등급/리워드 시스템

- **등급 산정**: 최근 7일간 누적 점수 기준
    
    - Bronze (0~~500), Silver (501~~2000), Gold (2001~5000), Platinum (5001+)
        
- **보상 지급**:
    
    - 승급 → 보너스 칩
        
    - 유지 → 소정 칩
        
    - 강등 → 보상 없음
        
- **배치 실행**: 매일 새벽 3시 `@Scheduled`
    

### 3.4 인게임 기능

#### 3.4.1 실시간 매칭

- 플레이어는 "게임 참여" 요청 → 대기열 큐 진입
    
- 대기열에 인원 충족 시 방 자동 생성 (`GameSession`)
    
- Redis Pub/Sub 또는 WebSocket 기반 알림
    

#### 3.4.2 칩 트랜잭션 관리

- 승패/보상/아이템 구매 시 칩 변동
    
- 모든 칩 변동은 원자적 트랜잭션으로 처리
    
- `ChipTransaction` 테이블에 이력 저장
    
- 동시성 문제 방지를 위한 **Optimistic Lock / Redis 분산락 적용**
    

#### 3.4.3 리더보드

- 상위 랭커(칩/점수 기준) 조회 API 제공
    
- Redis Sorted Set으로 실시간 랭킹 관리
    
- 매일 새벽 DB 동기화 배치 실행
    

---

## 4. 비기능 요구사항

- **성능**:
    
    - 10만 유저 기준 등급 배치 5분 이내 완료
        
    - 매칭 대기열 응답 시간 1초 이내
        
- **안정성**: 칩 트랜잭션 중 장애 발생 시 롤백 보장
    
- **확장성**: Redis 기반 매칭/랭킹 구조로 수평 확장 가능
    

---

## 5. 데이터 설계 (ERD)

```
User
 ├─ id (PK)
 ├─ nickname
 ├─ chips (보유 칩)
 ├─ rank (현재 등급)
 └─ created_at

GameHistory
 ├─ id (PK)
 ├─ user_id (FK → User)
 ├─ score
 ├─ result (win/lose)
 └─ played_at

RewardHistory
 ├─ id (PK)
 ├─ user_id (FK → User)
 ├─ reward_type (rank_up, rank_keep)
 ├─ amount
 └─ given_at

ChipTransaction
 ├─ id (PK)
 ├─ user_id (FK → User)
 ├─ change_amount (+/-)
 ├─ type (game_win, reward, purchase)
 └─ created_at

GameSession
 ├─ id (PK)
 ├─ player1_id (FK → User)
 ├─ player2_id (FK → User)
 ├─ status (waiting, playing, finished)
 └─ started_at
```

---

## 6. 배치 설계

### 6.1 등급 업데이트 배치

- Cron: `0 0 3 * * ?` (매일 새벽 3시)
    
- 최근 7일 `GameHistory` 집계 → 등급 갱신
    
- RewardService 호출하여 보상 지급
    

### 6.2 리더보드 동기화 배치

- Redis Sorted Set → RDB 동기화
    
- 랭킹 변동 로그 기록
    

### 6.3 성능 최적화 전략

- JPA Batch Insert/Update (`hibernate.jdbc.batch_size`)
    
- GameHistory `user_id + played_at` 복합 인덱스
    
- 페이징 처리로 대량 사용자 배치 업데이트
    

---

## 7. 부하 테스트 및 최적화

### 7.1 테스트 시나리오

1. **게임 기록 삽입 부하**
    
    - JMeter로 1,000 RPS 게임 기록 삽입
        
    - DB CPU, TPS 측정
        
2. **등급 배치 실행 부하**
    
    - 10만 유저, 1,000만 게임 기록 상태에서 실행 시간 측정
        
3. **리더보드 조회 부하**
    
    - 동시 1,000명 요청 시 응답 속도 측정
        

### 7.2 최적화 결과 목표

- 게임 기록 삽입 TPS 500 이상
    
- 등급 배치 5분 이내 완료
    
- 리더보드 조회 50ms 이하 응답
    
