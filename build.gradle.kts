plugins {
	java
	id("org.springframework.boot") version "3.5.8"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "dev.adrian"
version = "0.0.1-SNAPSHOT"
description = "SpringWeb"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // AGREGA ESTO: Driver de base de datos en memoria H2
    runtimeOnly("com.h2database:h2")

    // Lombook
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Web
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-test")

    // Pebble
    implementation("io.pebbletemplates:pebble-spring-boot-starter:3.2.2")
    implementation("io.pebbletemplates:pebble:3.2.2")

    // Security
    implementation("org.springframework.boot:spring-boot-starter-security")
}

tasks.withType<Test> {
	useJUnitPlatform()
}