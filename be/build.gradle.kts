plugins {
    id("buildlogic.java-application-conventions")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation(project(":entities"))
    runtimeOnly("com.h2database:h2")
}

application {
    // Define the main class for the application.
    mainClass = "com.valentin.mgmt.event.be.App"
}
