import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("java")
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.hibernate.orm") version "6.6.8.Final"
    id("org.graalvm.buildtools.native") version "0.10.5"
}

group = "org.panda.systems.kakeibon"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(23)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.4.3")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc:3.4.3")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.3")
    implementation("org.springframework.boot:spring-boot-starter-security:3.4.3")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.4.3")
    implementation("org.springframework.boot:spring-boot-starter-web:3.4.3")
    implementation("org.springframework.session:spring-session-core:3.4.2")
    implementation("org.springframework.boot:spring-boot-starter-json:3.4.3")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.3.RELEASE")
    implementation("jakarta.el:jakarta.el-api:6.0.1")
    implementation("jakarta.validation:jakarta.validation-api:3.1.1")
    implementation("jakarta.enterprise:jakarta.enterprise.cdi-api:4.1.0")
    implementation("org.json:json:20240303")
    developmentOnly("org.springframework.boot:spring-boot-devtools:3.4.3")
    runtimeOnly("org.postgresql:postgresql:42.7.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.3")
    testImplementation("org.springframework.security:spring-security-test:6.4.3")
    testImplementation("org.springframework:spring-test:6.2.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.platform:junit-platform-engine:1.12.0")
    testImplementation("org.mockito:mockito-core:5.16.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.16.0")
}

hibernate {
    enhancement {
        enableAssociationManagement = true
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    testLogging {
        showStandardStreams = true
        events("passed", "skipped", "failed")
        exceptionFormat = TestExceptionFormat.FULL
    }
    reports {
        html.outputLocation = file(layout.buildDirectory.dir("reports/junit5/html"))
        junitXml.outputLocation.set(
            layout.buildDirectory.dir("reports/junit5/xml"))
        isEnabled = true
    }
}