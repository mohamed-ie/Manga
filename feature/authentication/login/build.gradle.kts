plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.feature)
}

android {
    namespace = "com.manga.feature.latest_updated"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.data)
        }
    }
}