plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.library)
    alias(libs.plugins.manga.kotlin.multiplatform.koin)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.manage.mangadex.network"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.model)
            implementation(projects.core.network)

            //kotlinx
            api(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.datetime)
        }
    }
}
