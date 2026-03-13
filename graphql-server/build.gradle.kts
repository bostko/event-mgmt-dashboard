plugins {
    java
    application
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation(project(":domain"))
    implementation(project(":service"))
    runtimeOnly("org.postgresql:postgresql")
}

application {
    mainClass = "com.valentin.mgmt.event.generator.EventsGeneratorApplication"
}
