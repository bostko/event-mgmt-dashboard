plugins {
    base
    id("com.github.node-gradle.node") version "7.1.0"
}

node {
    version = "24.4.1"
    npmVersion = "11.4.2"
    download = false
}

tasks.named<com.github.gradle.node.npm.task.NpmTask>("npm_run_build") {
    inputs.files(fileTree("src"))
    inputs.file("angular.json")
    inputs.file("tsconfig.json")
    inputs.file("package.json")
    outputs.dir("dist")
}

tasks.register<com.github.gradle.node.npm.task.NpmTask>("npmBuild") {
    dependsOn("npmInstall")
    args = listOf("run", "build")
    inputs.files(fileTree("src"))
    inputs.file("angular.json")
    inputs.file("tsconfig.json")
    inputs.file("package.json")
    outputs.dir("dist")
}

tasks.named("assemble") {
    dependsOn("npmBuild")
}