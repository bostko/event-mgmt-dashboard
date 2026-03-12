plugins {
    java
    application
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("6.0.1")
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation(project(":entities"))
    runtimeOnly("com.h2database:h2")
}

application {
    mainClass = "com.valentin.mgmt.event.be.App"
}
