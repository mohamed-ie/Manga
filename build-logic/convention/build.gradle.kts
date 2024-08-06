plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradle)
    compileOnly(libs.kotlin.gradle)
    compileOnly(libs.compose.gradle)
    compileOnly(libs.ksp.gradle)
}



gradlePlugin {
    plugins {

        register("kotlin-multiplatform-app") {
            id = "manga.kotlin.multiplatform.app"
            implementationClass = "KotlinMultiplatformApplicationConventionPlugin"
        }

        register("kotlin-multiplatform-library") {
            id = "manga.kotlin.multiplatform.library"
            implementationClass = "KotlinMultiplatformLibraryConventionPlugin"
        }

        register("kotlin-multiplatform-koin") {
            id = "manga.kotlin.multiplatform.koin"
            implementationClass = "KotlinMultiplatformKoinConventionPlugin"
        }
    }
}