import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    `java-gradle-plugin`
    id("org.jetbrains.dokka")
}

buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(gradleApi())

    implementation(platform("org.http4k:http4k-bom:5.13.7.0"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-client-apache")
    implementation("org.apache.httpcomponents.client5:httpclient5:_")
    implementation("org.http4k:http4k-multipart")
    compileOnly("com.android.tools.build:gradle:7.0.3")
    implementation("com.google.code.gson:gson:2.10.1")

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

gradlePlugin {
    plugins {
        create(property("ID").toString()) {
            id = property("ID").toString()
            implementationClass = property("IMPLEMENTATION_CLASS").toString()
            version = property("VERSION").toString()
            description = property("DESCRIPTION").toString()
            displayName = property("DISPLAY_NAME").toString()
            tags.set(listOf("plugin", "gradle", "sample", "template"))
        }
    }
}

gradlePlugin {
    website.set(property("WEBSITE").toString())
    vcsUrl.set(property("VCS_URL").toString())
}
