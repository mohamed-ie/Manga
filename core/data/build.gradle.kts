plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.library)
    alias(libs.plugins.manga.kotlin.multiplatform.koin)
}

android {
    namespace = "com.manage.core.data"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.common)
            api(projects.core.model)
            api(projects.core.datastore)
            implementation(projects.core.network)

            //kotlinx
            api(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.datetime)

            //paging
            api(libs.androidx.paging.common)
        }

        androidMain.dependencies {
            //coroutines
            api(libs.kotlinx.coroutines.android)
        }
    }
}
