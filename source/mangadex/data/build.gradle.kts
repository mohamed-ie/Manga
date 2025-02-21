plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.library)
    alias(libs.plugins.manga.kotlin.multiplatform.koin)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.manage.mangadex.data"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.source.mangadex.network)
            api(projects.core.data)

            //kotlinx
            api(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.datetime)
        }

        androidMain.dependencies {
            //coroutines
            api(libs.kotlinx.coroutines.android)
        }
    }
}
