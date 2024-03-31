import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    `java-gradle-plugin`
    alias(libs.plugins.dokka)
    alias(libs.plugins.plugin.publish)
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

group = property("GROUP").toString()
version = property("VERSION").toString()

gradlePlugin {
    website.set(property("WEBSITE").toString())
    vcsUrl.set(property("VCS_URL").toString())
    plugins {
        create("dev.yabd.plugin") {
            id = property("ID").toString()
            implementationClass = property("IMPLEMENTATION_CLASS").toString()
            version = property("VERSION").toString()
            description = property("DESCRIPTION").toString()
            displayName = property("DISPLAY_NAME").toString()
            tags.set(listOf("android", "artifact", "slack", "jira", "telegram"))
        }
    }
}