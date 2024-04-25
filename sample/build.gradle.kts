import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("io.github.andrew-malitchuk.yabd")
}

android {
    namespace = "dev.yabd.sample"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.yabd.sample"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    flavorDimensions += "version"
    productFlavors {
        create("demo") {
            dimension = "version"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = freeCompilerArgs + "-Xcontext-receivers"
    }
}

yabd {
    val keystoreFile = project.rootProject.file("local.properties")
    val properties = Properties()
    properties.load(keystoreFile.inputStream())

    telegram {
        chatId = properties["chatId"] as String
        token = properties["telegramToken"] as String
    }
    jira {
        email = properties["email"] as String
        token = properties["jiraToken"] as String
        ticket = properties["ticket"] as String
        jiraCloudInstance = properties["jiraCloudInstance"] as String
    }
    jiraComment {
        email = properties["email"] as String
        token = properties["jiraToken"] as String
        ticket = properties["ticket"] as String
        jiraCloudInstance = properties["jiraCloudInstance"] as String
        comment = "Your build: {URL_TO_REPLACE}"
    }
    jiraAttachBuild {
        email = properties["email"] as String
        token = properties["jiraToken"] as String
        ticket = properties["ticket"] as String
        jiraCloudInstance = properties["jiraCloudInstance"] as String
    }
    slackConfig {
        channel = properties["slackChannel"] as String
        token = properties["slackToken"] as String
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    androidTestImplementation(platform(libs.compose.bom))
    debugImplementation(libs.ui.tooling)
}
