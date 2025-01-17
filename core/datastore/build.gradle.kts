plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.library)
    alias(libs.plugins.manga.kotlin.multiplatform.koin)
}

android {
    namespace = "com.manage.core.datastore"
}

kotlin{
    sourceSets{
        commonMain.dependencies {
            api(projects.core.common)
            api(projects.core.model)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.datastore)
            implementation(libs.kotlinx.serialization.protobuf)
            implementation(libs.okio)
        }

        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
        }
    }
}