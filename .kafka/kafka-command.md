## 도커 컴포우즈로 카프카 컨테이너 실행
```
docker compose -f docker-compose-single-kafka.yaml up -d         
```

## 실행 중인 카프카 컨테이너 중지
```
docker compose -f docker-compose-single-kafka.yaml stop  
```

## 실행 중인 카프카 컨테이너 삭제
```
docker compose -f docker-compose-single-kafka.yaml down  
```

## 카프카 컨테이너 내부 접속 (로컬 PC에 카프카가 설치되어있지 않다면)
로컬 PC에 카프카가 설치되어있지 않다면 kafka-topics 등 카프카 명령어를 사용할 수 없다.
카프카 컨테이너에 내부 접속하여 아래 나오는 명령어들을 실행.
단, 로컬 PC에 카프카 바이너리를 설치하면, 설치된 경로에서 명령어를 실행할 수 있다.
```
docker exec -it <kafka-container-id> /bin/bash
```


## 토픽 리스트 보기
어플리케이션 실행 후 토픽이 자동 생성된 것을 확인한다.
```
kafka-topics --list --bootstrap-server localhost:9092
```

## 알림센터 토픽 생성 (생략 가능)
어플리케이션 실행 후 토픽이 자동으로 생성되었다면 생략 가능.

### comment 토픽 생성
```
kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic comment
```
### like 토픽 생성
```
kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic like
```

### follow 토픽 생성
```
kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic follow
```


## 알림센터 이벤트 발행

### 댓글 이벤트
```
echo '{"type":"ADD","postId":1,"userId":2,"commentId":1}' | kafka-console-producer --broker-list localhost:9092 --topic comment
```

### 좋아요 이벤트
```
echo '{"type":"ADD","postId":1,"userId":2,"createdAt":"2024-01-28T18:35:24.01Z"}' | kafka-console-producer --broker-list localhost:9092 --topic like
```

### 팔로우 이벤트
```
echo '{"type":"ADD","userId":1,"targetUserId":2,"createdAt":"2024-01-28T18:35:24.01Z"}' | kafka-console-producer --broker-list localhost:9092 --topic follow
```


## 참고) 테스트 해보기

### 테스트 토픽 생성
컨테이너 내부에서 카프카 주제를 생성한다.

```
kafka-topics --create --topic test --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```

### 테스트 토픽에 이벤트 발행
테스트 토픽에 이벤트를 발생 시킴
```
kafka-console-producer --topic test --bootstrap-server localhost:9092
```

### 테스트 토픽에서 이벤트 수신
다른 터미널에서 카프카 컨테이너에 접속하여 이벤트를 수신한다.

```
kafka-console-consumer --topic test --bootstrap-server localhost:9092 --from-beginning
```


## 기타 참고
### 토픽 생성하기
$ kafka-topics.sh --create --topic board_topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1

### 생성된 토픽확인
$ kafka-topics.sh --describe --topic board_topic --bootstrap-server kafka:9092
$ cd /opt/kafka_2.13-2.8.1/bin/

### 컨슈머 실행하기
$ ./kafka-console-consumer.sh --topic board_topic --bootstrap-server kafka:9092

### 프로듀서 실행하기
$ ./kafka-console-producer.sh --topic board_topic --broker-list kafka:9092

##3 컨슈머 결과 보기
$ ./kafka-console-consumer.sh --topic board_topic --bootstrap-server localhost:9092 --from-beginning
$ ./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic <topic-name> --from-beginning