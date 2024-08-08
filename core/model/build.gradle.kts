import com.build_logic.convention.utils.sourceSets

plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.library)
}

android {
    namespace = "com.manage.core.model"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.datetime)
        }
    }
}
