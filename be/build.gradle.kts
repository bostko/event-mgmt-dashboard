plugins {
    id("buildlogic.java-application-conventions")
    id("org.springframework.boot") version "4.0.3"
    id("io.spring.dependency-management") version "1.1.7"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation(project(":entities"))
}

application {
    // Define the main class for the application.
    mainClass = "com.valentin.mgmt.event.be.App"
}
