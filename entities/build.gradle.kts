plugins {
    `java-library`
    id("io.freefair.lombok") version "9.2.0"
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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
