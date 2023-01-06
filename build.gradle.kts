plugins {
	java
	id("org.springframework.boot") version "2.7.8-SNAPSHOT"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "com.romero"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

extra["springCloudVersion"] = "2021.0.5"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.projectlombok:lombok:1.18.24")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok:1.18.24")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

