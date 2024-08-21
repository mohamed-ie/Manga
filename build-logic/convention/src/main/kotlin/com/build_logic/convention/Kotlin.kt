package com.build_logic.convention

import com.android.build.api.dsl.CommonExtension
import com.build_logic.convention.utils.sourceSets
import com.build_logic.convention.utils.versionInt
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.api.resources.TextResource
import org.gradle.api.tasks.Sync
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

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
                excludes += "/META-INF/LICENSE.md"
                excludes += "/META-INF/LICENSE-notice.md"
                excludes += "/META-INF/*.kotlin_module"
                excludes += "/META-INF/DEPENDENCIES"
            }
        }
    }
    configureKotlin()
}

internal fun Project.configureKotlinJvm() {
    kotlinExtension.jvmToolchain(17)
}

private fun Project.configureKotlin() {
    configureKotlinJvm()

    tasks.withType<KotlinCompile> {
        compilerOptions.freeCompilerArgs.addAll(
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.coroutines.FlowPreview",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-opt-in=androidx.compose.animation.ExperimentalSharedTransitionApi",
            "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-Xcontext-receivers"
        )
    }
}

internal fun Project.configureKotlinMultiplatform(
    kotlinMultiplatformExtension: KotlinMultiplatformExtension,
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    kotlinMultiplatformExtension.apply {
        configureKotlinJvm()
    }

    configureKotlinAndroid(commonExtension)
}

internal fun Project.configureKotlinMultiplatformLibrary(
    kotlinMultiplatformExtension: KotlinMultiplatformExtension,
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    kotlinMultiplatformExtension.apply {
        configureKotlinJvm()
        jvm("desktop")
        androidTarget()
        iosX64()
        iosArm64()
        iosSimulatorArm64()
        sourceSets.commonMain.configure {
            sourceSets {
                kotlin.srcDir(buildConfigGenerator.map { it.destinationDir })
            }
        }
    }
    configureKotlinAndroid(commonExtension)
}

val Project.buildConfigGenerator
    get() = tasks.register<Sync>("buildConfigGenerator") {
        val properties =
            Properties().apply { load(project.rootProject.file("local.properties").inputStream()) }

        // create a provider for the project version
        val projectVersionProvider: Provider<String> = provider { project.version.toString() }

        // map the project version into a file
        val buildConfigFileContents: Provider<TextResource> =
            projectVersionProvider.map { version ->
                resources.text.fromString(
                    """
          |package ${project.name}
          |
          |object BuildConfig {
          |  const val PROJECT_VERSION = "$version"
          |  const val MANGA_DEX_URL = "${properties["MANGA_DEX_URL"]}"
          |  const val DEBUG = ${project.hasProperty("debug") && project.property("debug") == "true"}
          |}
          |
        """.trimMargin()
                )
            }

        // Gradle accepts file providers as Sync inputs
        from(buildConfigFileContents) {
            rename { "BuildConfig.kt" }
            into(project.name)
        }

        into(layout.buildDirectory.dir("generated-src/kotlin/"))
    }