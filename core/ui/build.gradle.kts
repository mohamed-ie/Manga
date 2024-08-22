
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
        val commonMain by getting
        commonMain.dependencies {
            api(projects.core.designSystem)
            api(projects.core.model)
            api(projects.core.common)
            api(compose.components.resources)

            implementation(libs.paging.common)
            api(libs.kotlinx.datetime)

            //coil
            api(libs.coil)
            api(libs.coil.compose)
            api(libs.coil.compose.core)
            api(libs.coil.network.ktor2)
        }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
}