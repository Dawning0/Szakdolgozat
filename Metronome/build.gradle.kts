// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("org.jetbrains.dokka") version "2.0.0"
}
tasks.dokkaHtml {
    outputDirectory.set(buildDir.resolve("dokka/html"))
    dokkaSourceSets {
        configureEach {
            includeNonPublic.set(false)
            skipEmptyPackages.set(true)
        }
    }
}
dependencies {
    dokkaPlugin("org.jetbrains.dokka:android-documentation-plugin:2.0.0")
}