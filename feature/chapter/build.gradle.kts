plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.feature)
}

android {
    namespace = "com.manage.feature.chapter"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.data)
        }
    }
}
