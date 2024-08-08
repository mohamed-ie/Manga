
plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.manage.core.ui"
    dependencies {
        debugApi(compose.uiTooling)
    }
}

kotlin{
    sourceSets{
        val commonMain by getting
        val androidMain by getting
        val desktopMain by getting

        commonMain.dependencies {
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.materialIconsExtended)
            api(compose.ui)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)
            api(compose.uiUtil)
        }

        androidMain.dependencies {
            api(compose.preview)
            api(libs.androidx.activity.compose)
        }

        desktopMain.dependencies {
            api(compose.desktop.currentOs)
        }
    }
}