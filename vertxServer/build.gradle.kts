import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin ("jvm")
  application
  id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.example"
version = "1.0.0-SNAPSHOT"

repositories {
  mavenCentral()
}

application {
  mainClass.set("com.example.starter.MainKt")
}

dependencies {
  api(project(":PodNetwork"))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "17"

tasks.withType<ShadowJar> {
  archiveClassifier.set("fat")
  manifest {
    attributes(mapOf("Main-Verticle" to "com.example.starter.MainVerticle"))
  }
  mergeServiceFiles()
}

tasks.register("serve") {
  dependsOn("shadowJar")
  doLast {
    val fatJar = tasks.shadowJar.get().archiveFile.get().asFile
    exec {
      commandLine("java", "-jar", fatJar.absolutePath)
    }
  }
}

