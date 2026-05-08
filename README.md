# Notification Service

비즈니스 이벤트를 입력으로 받아 알림 요청을 저장하고, 비동기 워커를 통해 채널별 발송을 처리하는 알림 서버입니다.
외부 채널 실패를 고려해 retry/backoff, idempotency, 상태 추적, delivery attempt 기록, 운영자 재처리 기능을 구현했습니다.

## 핵심 기능
- 알림 요청 저장 API
- 비동기 워커 기반 발송 처리
- retry + backoff
- idempotency key 기반 중복 방지
- delivery_attempt / admin_action_log 기록
- 관리자 조회 / 재처리 / 통계 기능

## 기술 스택
- Backend: Spring Boot, MyBatis, Oracle
- Frontend: React
- Channel: Email, Solapi SMS, Push Mock

## 시스템 구조

```text
[Business Service / Admin Console]
              ↓ REST API
        [Notification API]
              ↓
      [Notification 저장(DB)]
              ↓
        [Notification Worker]
              ↓
 [NotificationSenderResolver]
      ↓         ↓          ↓
    Email    Solapi SMS   Push Mock
```

## ERD

<img width="519" height="281" alt="image" src="https://github.com/user-attachments/assets/a3b829f3-9ae5-42cc-ac5c-5742fe10d979" />

## 상태 흐름

```text
PENDING
  ↓
IN_PROGRESS
  ├─ 성공 → SENT
  └─ 실패 → RETRY_SCHEDULED
                  ↓
             IN_PROGRESS
                  ├─ 성공 → SENT
                  └─ 재시도 한계 초과 → DEAD_LETTER

수동 재처리:
DEAD_LETTER / RETRY_SCHEDULED → PENDING
```

## 주요 API
- POST /api/notifications
- GET /api/notifications
- GET /api/notifications/{id}
- GET /api/notifications/{id}/attempts
- GET /api/notifications/{id}/admin-actions
- POST /api/notifications/{id}/retry
- GET /api/notifications/stats

## 테스트 시나리오
- 생성 → SENT
- 실패 → RETRY_SCHEDULED
- 재시도 후 성공 → SENT
- 최대 재시도 초과 → DEAD_LETTER
- 수동 재처리
- idempotency 검증

## 실행 화면
