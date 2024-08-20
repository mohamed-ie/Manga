import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.manga.kotlin.multiplatform.app)
    alias(libs.plugins.ksp)
}

kotlin {
    androidTarget()
    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "app"
            isStatic = true
        }
    }

    sourceSets {
        val desktopMain by getting

        val androidMain by getting

        val commonMain by getting {
            kotlin.srcDirs("build/generated/ksp/main/kotlin")
        }

        commonMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.core.data)
            //koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            api(project.dependencies.platform(libs.koin.annotations.bom))
            api(libs.koin.annotations)
        }

        androidMain.dependencies {
            //koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.androidx.compose)
        }

    }
}

ksp {
    arg("KOIN_USE_COMPOSE_VIEWMODEL", "true")
    arg("KOIN_DEFAULT_MODULE", "false")
}

dependencies {
    //koin
    add("kspCommonMainMetadata", platform(libs.koin.annotations.bom))
    add("kspCommonMainMetadata", libs.koin.ksp.compiler)
    add("kspAndroid", platform(libs.koin.annotations.bom))
    add("kspAndroid", libs.koin.ksp.compiler)
}

android {
    namespace = "com.manga.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.manga.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.manga.app"
            packageVersion = "1.0.0"
        }
    }
}
