package com.build_logic.convention

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.build_logic.convention.utils.version
import com.build_logic.convention.utils.versionInt
import org.gradle.kotlin.dsl.getByType
import com.android.build.api.dsl.CommonExtension
import com.build_logic.convention.utils.sourceSets
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.api.resources.TextResource
import org.gradle.api.tasks.Sync
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.support.kotlinCompilerOptions
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

fun Project.configureKotlinAndroidApplication(
    appExtension: BaseAppModuleExtension = extensions.getByType<BaseAppModuleExtension>()
) {
    appExtension.apply {
        defaultConfig {
            targetSdk = versionInt("android-targetSdk")
            versionCode = versionInt("versionCode")
            versionName = version("versionName")
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                isShrinkResources = true
            }
        }
    }

    configureKotlinAndroidCompose(appExtension)
}

fun Project.configureKotlinAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }
    }

    configureKotlinAndroid(commonExtension)
}

fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        compileSdk = versionInt("android-compileSdk")

        defaultConfig {
            minSdk = versionInt("android-minSdk")
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
}

fun Project.configureKotlinMultiplatform(
    kotlinMultiplatformExtension: KotlinMultiplatformExtension = extensions.getByType<KotlinMultiplatformExtension>(),
) {
    kotlinMultiplatformExtension.apply {
        jvm("desktop")
        androidTarget()
        jvmToolchain(versionInt("jdk"))
        sourceSets {
            commonMain {
                kotlin.srcDir(commonBuildConfigGenerator.map { it.destinationDir })
                compilerOptions{
                    freeCompilerArgs.addAll("-Xcontext-receivers")
                }
            }
        }
    }
}

val Project.commonBuildConfigGenerator
    get() = tasks.register<Sync>("commonBuildConfigGenerator") {
        val properties =
            Properties().apply { load(project.rootProject.file("local.properties").inputStream()) }

        // create a provider for the project version
        val projectVersionProvider: Provider<String> = provider { project.version.toString() }

        val idDebug = project.gradle.startParameter.taskNames.any { it.contains("Debug") }

        // map the project version into a file
        val buildConfigFileContents: Provider<TextResource> =
            projectVersionProvider.map { version ->
                resources.text.fromString(
                    """
          |package ${project.name}
          |
          |internal object CommonBuildConfig {
          |     const val VERSION = "$version"
          |     const val DEBUG = $idDebug
          |     const val MANGA_DEX_URL = "${properties["MANGA_DEX_URL"]}"
          |     const val MANGA_DEX_COVER_URL = "${properties["MANGA_DEX_COVER_URL"]}"
          |}
          |
        """.trimMargin()
                )
            }

        // Gradle accepts file providers as Sync inputs
        from(buildConfigFileContents) {
            rename { "CommonBuildConfig.kt" }
            into(project.name)
        }

        into(layout.buildDirectory.dir("generated-src/kotlin/"))
    }