plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.feature)
    alias(libs.plugins.manga.kotlin.multiplatform.koin)
}

android {
    namespace = "com.manga.feature.more.more"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.data)
        }
    }
}