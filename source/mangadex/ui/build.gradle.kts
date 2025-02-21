plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.library)
    alias(libs.plugins.manga.kotlin.multiplatform.koin)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.manage.mangadex.ui"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.components.resources)
            implementation(compose.runtime)
            implementation(projects.source.mangadex.data)
        }
    }
}
