
import com.build_logic.convention.utils.library
import com.build_logic.convention.utils.sourceSets
import com.google.devtools.ksp.gradle.KspExtension
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

        extensions.getByType<KspExtension>().apply {
            arg("KOIN_USE_COMPOSE_VIEWMODEL","true")
            arg("KOIN_USE_COMPOSE_VIEWMODEL","true")
            arg("USE_COMPOSE_VIEWMODEL","true")
        }

        val kotlinMultiplatformExtension = extensions.getByType<KotlinMultiplatformExtension>()

        kotlinMultiplatformExtension.apply {
            sourceSets {
                commonMain.dependencies {
                    implementation(project(":core:ui"))
                    implementation(project(":core:model"))
                    implementation(project(":core:common"))

                    implementation(project.dependencies.platform(library("koin-bom")))
                    implementation(library("koin.compose.viewmodel"))

                    implementation(compose("org.jetbrains.compose.components:components-resources"))
                    implementation(library("lifecycle.viewmodel.compose"))
                }
            }
        }
        Unit
    }
}