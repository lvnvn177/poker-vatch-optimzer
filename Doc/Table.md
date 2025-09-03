## 1. 회원 (User)

|Method|Endpoint|설명|Request|Response|
|---|---|---|---|---|
|`POST`|`/api/v1/users/signup`|회원가입|`{ "nickname": "player1", "password": "1234" }`|`{ "id": 1, "nickname": "player1", "chips": 1000, "rank": "BRONZE" }`|
|`POST`|`/api/v1/users/login`|로그인 (JWT 발급)|`{ "nickname": "player1", "password": "1234" }`|`{ "token": "jwt-token-string" }`|
|`GET`|`/api/v1/users/me`|내 정보 조회|Header: `Authorization: Bearer <token>`|`{ "id": 1, "nickname": "player1", "chips": 1200, "rank": "SILVER" }`|

---

## 2. 게임 기록 (GameHistory)

|Method|Endpoint|설명|Request|Response|
|---|---|---|---|---|
|`POST`|`/api/v1/games/play`|게임 플레이 (간단한 랜덤 승패)|`{ "betAmount": 100 }`|`{ "gameId": 10, "result": "WIN", "score": 200, "chips": 1300 }`|
|`GET`|`/api/v1/games/history`|내 게임 기록 조회|Query: `page, size`|`{ "content": [ { "id": 1, "result": "WIN", "score": 200, "playedAt": "2025-09-02T10:00:00" } ] }`|

---

## 3. 등급/리워드 (Batch 결과 조회)

|Method|Endpoint|설명|Request|Response|
|---|---|---|---|---|
|`GET`|`/api/v1/rank`|내 등급 조회|-|`{ "rank": "GOLD", "scoreLast7Days": 3200 }`|
|`GET`|`/api/v1/rewards/history`|보상 내역 조회|Query: `page, size`|`{ "content": [ { "id": 1, "rewardType": "RANK_UP", "amount": 500, "givenAt": "2025-09-02T03:00:00" } ] }`|

---

## 4. 매칭 (Matching)

|Method|Endpoint|설명|Request|Response|
|---|---|---|---|---|
|`POST`|`/api/v1/match/join`|매칭 큐 참여|-|`{ "status": "WAITING", "queueSize": 3 }`|
|`POST`|`/api/v1/match/leave`|매칭 큐 나가기|-|`{ "status": "LEFT" }`|
|`GET`|`/api/v1/match/status`|현재 매칭 상태 확인|-|`{ "status": "MATCHED", "gameSessionId": 12 }`|

---

## 5. 칩 트랜잭션 (ChipTransaction)

|Method|Endpoint|설명|Request|Response|
|---|---|---|---|---|
|`POST`|`/api/v1/chips/transfer`|칩 변동 처리 (아이템 구매 등)|`{ "amount": -200, "type": "PURCHASE" }`|`{ "chips": 1100 }`|
|`GET`|`/api/v1/chips/history`|칩 변동 내역 조회|Query: `page, size`|`{ "content": [ { "id": 1, "changeAmount": +200, "type": "GAME_WIN", "createdAt": "2025-09-02T12:00:00" } ] }`|

---

## 6. 리더보드 (Leaderboard)

|Method|Endpoint|설명|Request|Response|
|---|---|---|---|---|
|`GET`|`/api/v1/leaderboard/chips`|칩 보유량 랭킹 조회|Query: `top=100`|`{ "rankings": [ { "rank": 1, "nickname": "player1", "chips": 9999 }, ... ] }`|
|`GET`|`/api/v1/leaderboard/score`|점수 랭킹 조회|Query: `top=100`|`{ "rankings": [ { "rank": 1, "nickname": "player2", "score": 5000 }, ... ] }`|

---

# ERD (Entity Relationship Diagram)

```
┌─────────────────────┐
│       User          │
├─────────────────────┤
│ id (PK)             │
│ nickname            │
│ password            │
│ chips               │
│ rank                │
│ created_at          │
└─────────┬───────────┘
          │ 1
          │
          │ N
┌─────────────────────┐
│   GameHistory       │
├─────────────────────┤
│ id (PK)             │
│ user_id (FK)        │
│ score               │
│ result              │
│ played_at           │
└─────────────────────┘

┌─────────────────────┐
│   RewardHistory     │
├─────────────────────┤
│ id (PK)             │
│ user_id (FK)        │
│ reward_type         │
│ amount              │
│ given_at            │
└─────────────────────┘

┌─────────────────────┐
│  ChipTransaction    │
├─────────────────────┤
│ id (PK)             │
│ user_id (FK)        │
│ change_amount       │
│ type                │
│ created_at          │
└─────────────────────┘

┌─────────────────────┐
│   GameSession       │
├─────────────────────┤
│ id (PK)             │
│ player1_id (FK)     │
│ player2_id (FK)     │
│ status              │
│ started_at          │
└─────────────────────┘
```

---

