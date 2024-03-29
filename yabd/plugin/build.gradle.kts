import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    `java-gradle-plugin`
    id("org.jetbrains.dokka")
    id("com.gradle.plugin-publish") version "1.2.1"
}

buildscript {
    dependencies {
        classpath(libs.android.gradle.plugin)
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(gradleApi())
    implementation(libs.bundles.http4k)
    implementation(libs.httpclient5)
    compileOnly(libs.android.gradle.plugin)
    implementation(libs.gson)
    testImplementation(libs.junit)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = freeCompilerArgs + "-Xcontext-receivers"
    }
}

group = "dev.yabd.plugin"
version = "0.0.1-a.1"

gradlePlugin {
    website.set("https://github.com/andrew-malitchuk/yet-another-build-dispatcher")
    vcsUrl.set("https://github.com/andrew-malitchuk/yet-another-build-dispatcher")
    plugins {
        create("dev.yabd.plugin") {
            id = "dev.yabd.plugin"
            implementationClass = "dev.yabd.plugin.internal.YabdPlugin"
            version = "0.0.1-a.1"
            description =
                "A handy Gradle plugin for automating build distribution to Slack, Telegram, and Jira."
            displayName = "[YABD] Yet Another Build Dispatcher"
            tags.set(listOf("android", "artifact", "slack", "jira", "telegram"))
        }
    }
}