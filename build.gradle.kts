buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

plugins {
    id("org.jetbrains.intellij") version "0.7.3"
    kotlin("jvm") version "1.4.20"
}

intellij {
    version = "2021.3"
    pluginName = "JsonToKotlinClass"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.20")
    testImplementation("com.winterbe:expekt:0.5.0") {
        exclude(group = "org.jetbrains.kotlin")
    }
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

}
