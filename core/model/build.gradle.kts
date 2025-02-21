import com.build_logic.convention.utils.sourceSets

plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.library)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.manage.core.model"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.datetime)
            api(libs.kotlinx.collections.immutable)
            api(libs.kotlinx.serialization.json)
        }
    }
}
