@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.0"
    `java-library`
}

repositories {
    jcenter()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

dependencies {

    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.4.0")

    implementation("software.amazon.awssdk:sqs:2.15.47")

    implementation("org.slf4j:slf4j-api:1.7.36")

    testImplementation("org.slf4j:slf4j-log4j12:1.7.30")

    testImplementation("org.jetbrains.kotlin:kotlin-test")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

 }
