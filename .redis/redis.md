# Redis

#### docker-compose -f docker-compose-single-redis.yaml up -d



```yaml
services:
  redis:
    image: redis:7.4.0
    command: redis-server --port 6379
    container_name: redis_standalone
    hostname: localhost
    labels:
      - "name=redis"
      - "mode=standalone"
    ports:
      - 6379:6379
```

#### redis-cli

```bash
>> docker exec -it redis_boot redis-cli
127.0.0.1:6379> PING
PONG
127.0.0.1:6379> keys *
(empty array)
127.0.0.1:6379> SET abc 123
OK
127.0.0.1:6379> GET abc
"123"
127.0.0.1:6379>
```