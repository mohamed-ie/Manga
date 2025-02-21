plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.feature)
    alias(libs.plugins.manga.kotlin.multiplatform.koin)
}

android {
    namespace = "com.manga.feature.explore"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.data)
            implementation(libs.androidx.paging.common)
        }
    }
}