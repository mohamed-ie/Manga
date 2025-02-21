plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.app)
    alias(libs.plugins.manga.kotlin.multiplatform.koin)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
}

kotlin {
    androidTarget()
    jvm("desktop")

    sourceSets {
        val androidMain by getting

        commonMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.core.data)
            implementation(projects.source.mangadex.data)
            implementation(projects.source.mangadex.network)
            implementation(projects.source.mangadex.ui)
            implementation(projects.core.datastore)
            implementation(projects.core.domain)
            implementation(projects.core.network)

            implementation(projects.feature.manga.explore)
            implementation(projects.feature.onboarding)
            implementation(projects.feature.library)
            implementation(projects.feature.more)

            implementation(libs.androidx.navigation.compose)

            implementation(libs.napier)

            //koin
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.koin.compose)
        }

        androidMain.dependencies {
            //koin
            implementation(libs.koin.androidx.compose)
            implementation(libs.androidx.core.splashscreen)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }

    }
}

android {
    namespace = "com.manga.app"
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            packageName = "com.manga.app"
        }
    }
}
