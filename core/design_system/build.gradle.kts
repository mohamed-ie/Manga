plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin{
    sourceSets{
        val desktopMain by getting
        commonMain.dependencies {
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.ui)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)
            api(libs.androidx.lifecycle.viewmodel)
            api(libs.androidx.lifecycle.runtime.compose)
            api(libs.androidx.navigation.compose)

            implementation(compose.materialIconsExtended)
        }

        androidMain.dependencies {
            api(libs.androidx.activity.compose)
            api(compose.preview)
        }

        desktopMain.dependencies {
            api(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "com.manga.core.design_system"
}

dependencies {
    debugApi(compose.uiTooling)
}