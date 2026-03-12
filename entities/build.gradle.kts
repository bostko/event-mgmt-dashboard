plugins {
    id("buildlogic.java-library-conventions")
    id("io.freefair.lombok") version "9.2.0"
}

dependencies {
//    implementation("org.springframework.data:spring-data-rest-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}