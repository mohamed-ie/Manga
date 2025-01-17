plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.library)
    alias(libs.plugins.manga.kotlin.multiplatform.koin)
}

android {
    namespace = "com.manage.core.common"
}

kotlin{
    sourceSets{
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
        }

        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
        }
    }
}