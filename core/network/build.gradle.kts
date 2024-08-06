plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.library)
    alias(libs.plugins.manga.kotlin.multiplatform.koin)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.manage.core.common"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.common)
            api(projects.core.model)

            //ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)

            //kotlinx
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)

            //paging
            implementation(libs.paging.common)
        }

        androidMain.dependencies {
            //ktor
            implementation(libs.ktor.client.android)

            //coroutines
            implementation(libs.kotlinx.coroutines.android)
        }

        iosMain.dependencies {
            //ktor
            implementation(libs.ktor.client.darwin)
        }
    }
}