import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.6"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id("org.sonarqube") version "4.0.0.2929"
	kotlin("jvm") version "1.8.20"
	kotlin("plugin.spring") version "1.8.20"
	jacoco
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
	sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	testImplementation(kotlin("test"))
	testImplementation("io.kotest:kotest-runner-junit5-jvm:5.6.1")
	testImplementation("io.mockk:mockk:1.13.5")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

sonarqube {
	properties {
		property("sonar.projectKey", "demo")
		property("sonar.host.url", "http://localhost:9000")
		property("sonar.junit.reportPaths", "build/test-results/test")
		property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
		property("sonar.exclusions", "src/test/**/*")
	}
}

jacoco {
	toolVersion = "0.8.9"
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
	if (project.hasProperty("spring.profiles.active")) {
		systemProperty("spring.profiles.active", project.findProperty("spring.profiles.active") ?: "local")
	}
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
	}
}