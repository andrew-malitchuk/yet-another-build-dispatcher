import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    id("org.jetbrains.dokka") version "1.9.10"
//    id("com.gradle.plugin-publish") version "1.2.1"
}

allprojects {
//    group = "dev.yabd.plugin"
//    version =  "0.0.1-a.1"

    apply {
        plugin(rootProject.libs.plugins.detekt.get().pluginId)
        plugin(rootProject.libs.plugins.ktlint.get().pluginId)
    }

    ktlint {
        debug.set(false)
        verbose.set(true)
        android.set(false)
        outputToConsole.set(true)
        ignoreFailures.set(false)
        enableExperimentalRules.set(true)
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }

    detekt {
        config.setFrom(rootProject.files("../config/detekt/detekt.yml"))
    }
}

//gradlePlugin {
//    website.set("https://github.com/andrew-malitchuk/yet-another-build-dispatcher")
//    vcsUrl.set("https://github.com/andrew-malitchuk/yet-another-build-dispatcher")
//    plugins {
//        create("dev.yabd.plugin") {
//            id = "dev.yabd.plugin"
//            implementationClass = "dev.yabd.plugin.internal.YabdPlugin"
//            version = "0.0.1-a.1"
//            description =
//                "A handy Gradle plugin for automating build distribution to Slack, Telegram, and Jira."
//            displayName = "[YABD] Yet Another Build Dispatcher"
//            tags.set(listOf("android", "gradle", "artifact", "slack", "jira", "telegram"))
//        }
//    }
//}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        html.outputLocation.set(file("build/reports/detekt.html"))
    }
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}
