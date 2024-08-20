plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.feature)
}

android {
    namespace = "com.manage.feature.manga.recent_updated"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.domain)
        }
    }
}