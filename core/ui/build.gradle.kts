plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.library)
    alias(libs.plugins.manga.kotlin.multiplatform.koin)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.manage.core.ui"
}

kotlin{
    sourceSets{
        commonMain.dependencies {
            api(projects.core.designSystem)
            api(projects.core.model)
            api(projects.core.data)
            api(projects.core.common)

            implementation(compose.components.resources)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.androidx.paging.common)
            implementation(libs.coil.compose)
            api(libs.kotlinx.datetime)
            api(libs.kotlinx.collections.immutable)
            api(libs.compose.utils.app.event)

            api(libs.coil)
            api(libs.coil.svg)
            api(libs.coil.compose.core)
            api(libs.coil.network.ktor3)

            api(libs.kotlinx.coroutines.core)
        }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
}