## 빌드 환경

```
Java 18 (18.0.2-zulu)
Gradle
Spring Boot 2.7.3
```

## 프로그램 빌드 및 실행

```
./gradlew clean build
java -jar build/libs/reward-api.jar
```

## API 명세 (RestDocs)

```
http://localhost:8080/docs/index.html
```

### Preview

```http request
### 보상 데이터 조회 API
GET http://localhost:8080/v1/reward
Accept: application/json

### 보상 지급 API
POST http://localhost:8080/v1/reward/apply
Content-Type: application/x-www-form-urlencoded
X-User-ID: 10

### 보상 조회 API
GET http://localhost:8080/v1/reward/history?rewardDate=2022-09-25
Accept: application/json
```
