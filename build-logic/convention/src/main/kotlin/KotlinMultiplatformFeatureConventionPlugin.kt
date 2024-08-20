
import com.android.build.api.dsl.LibraryExtension
import com.build_logic.convention.configureKotlinMultiplatform
import com.build_logic.convention.utils.library
import com.build_logic.convention.utils.sourceSets
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("manga.kotlin.multiplatform.library")
            apply("manga.kotlin.multiplatform.koin")
            apply("org.jetbrains.compose")
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        val kotlinMultiplatformExtension = extensions.getByType<KotlinMultiplatformExtension>()

        configureKotlinMultiplatform(
            kotlinMultiplatformExtension = kotlinMultiplatformExtension,
            commonExtension = extensions.getByType<LibraryExtension>()
        )

        kotlinMultiplatformExtension.apply {
            sourceSets {
                commonMain.dependencies {
                    implementation(project(":core:ui"))
                    implementation(project(":core:model"))
                    implementation(project(":core:common"))
                    implementation(compose("org.jetbrains.compose.components:components-resources"))
                    implementation(library("lifecycle.viewmodel.compose"))
                }
            }
        }
        Unit
    }
}