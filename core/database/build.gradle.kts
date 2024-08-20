plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.library)
    alias(libs.plugins.manga.kotlin.multiplatform.koin)
}

android {
    namespace = "com.manage.core.database"
}

kotlin{
    sourceSets{
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
        }

        androidNativeMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
        }
    }
}