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
            implementation(projects.core.datastore)
            implementation(projects.core.domain)
            implementation(projects.core.network)

            implementation(projects.feature.home)
            implementation(projects.feature.manga.latestUpdated)
            implementation(projects.feature.chapter)
            implementation(projects.feature.more.more)

            implementation(libs.androidx.navigation.compose)

            implementation(libs.napier)

            //koin
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.koin.compose)
        }

        androidMain.dependencies {
            //koin
            implementation(libs.koin.androidx.compose)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }

    }
}

ksp {
    arg("KOIN_USE_COMPOSE_VIEWMODEL", "true")
    arg("KOIN_DEFAULT_MODULE", "false")
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
