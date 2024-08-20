plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.library)
    alias(libs.plugins.manga.kotlin.multiplatform.koin)
}

android {
    namespace = "com.manage.core.domain"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.common)
            api(projects.core.model)
            implementation(projects.core.data)

            //kotlinx
            api(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.datetime)

            //paging
            api(libs.paging.common)
        }

        androidMain.dependencies {
            //coroutines
            api(libs.kotlinx.coroutines.android)
        }
    }
}
