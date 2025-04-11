dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    // https://docs.spring.io/spring-cloud-stream/reference/kafka/kafka-binder/usage.html
    api("org.springframework.cloud:spring-cloud-starter-stream-kafka")
}
