
plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "org.manage.core.ui"
}

kotlin{
    sourceSets{
        commonMain.dependencies {
            api(projects.core.designSystem)
            api(projects.core.model)
            api(projects.core.common)
            implementation(compose.components.resources)
            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.paging.common)
            api(libs.kotlinx.datetime)
            api(libs.kotlinx.collections.immutable)
            api(libs.compose.utils.app.event)

            //coil
            api(libs.coil)
            implementation(libs.coil.compose)
            api(libs.coil.compose.core)
            api(libs.coil.network.ktor2)
        }

        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
        }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
}