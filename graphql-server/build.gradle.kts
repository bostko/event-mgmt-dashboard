plugins {
    java
    application
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation(project(":domain"))
    implementation(project(":service"))
    runtimeOnly("org.postgresql:postgresql")
}

application {
    mainClass = "com.valentin.mgmt.event.graphql.GraphqlSpringBootApplication"
}
