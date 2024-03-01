@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("dev.yabd.plugin")
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
        create("foobar") {
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
    telegram {
        chatId = project.properties["chatId"] as String
        token = project.properties["telegramToken"] as String
    }
    jira {
        email = project.properties["email"] as String
        token = project.properties["jiraToken"] as String
        ticket = project.properties["ticket"] as String
        jiraCloudInstance = project.properties["jiraCloudInstance"] as String
    }
    jiraComment {
        email = project.properties["email"] as String
        token = project.properties["jiraToken"] as String
        ticket = project.properties["ticket"] as String
        jiraCloudInstance = project.properties["jiraCloudInstance"] as String
        comment = "Your build: {URL_TO_REPLACE}"
    }
    jiraAttachBuild {
        email = project.properties["email"] as String
        token = project.properties["jiraToken"] as String
        ticket = project.properties["ticket"] as String
        jiraCloudInstance = project.properties["jiraCloudInstance"] as String
        comment = "Your build: {URL_TO_REPLACE}"
    }
    slackConfig {
        channel = project.properties["slackChannel"] as String
        token = project.properties["slackToken"] as String
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}
