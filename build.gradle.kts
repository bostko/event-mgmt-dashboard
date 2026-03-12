plugins {
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management") apply false
}

subprojects {
    apply(plugin = "io.spring.dependency-management")
    configure<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension> {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }
}