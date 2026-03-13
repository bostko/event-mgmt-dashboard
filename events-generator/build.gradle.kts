plugins {
    java
    application
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation(project(":domain"))
    runtimeOnly("com.h2database:h2")
}

application {
    mainClass = "com.valentin.mgmt.event.generator.EventsGeneratorApplication"
}