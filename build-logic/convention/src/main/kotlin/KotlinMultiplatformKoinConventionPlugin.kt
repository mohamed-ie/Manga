import com.build_logic.convention.utils.library
import com.build_logic.convention.utils.sourceSets
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformKoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.google.devtools.ksp")
        }

        extensions.getByType<KspExtension>().apply {
            arg("KOIN_DEFAULT_MODULE", "false")
            arg("KOIN_CONFIG_CHECK", "false")
        }

        extensions.getByType<KotlinMultiplatformExtension>().apply {
            sourceSets {
                commonMain {
                    kotlin.srcDirs("build/generated/ksp/main/kotlin")

                    dependencies {
                        implementation(project.dependencies.platform(library("koin.bom")))
                        implementation(library("koin.core"))
                        api(project.dependencies.platform(library("koin.annotations.bom")))
                        api(library("koin.annotations"))
                    }
                }

                androidMain.dependencies {
                    implementation(project.dependencies.platform(library("koin.bom")))
                    implementation(library("koin.android"))
                }
            }

            dependencies {
                add("kspCommonMainMetadata", platform(library("koin.annotations.bom")))
                add("kspCommonMainMetadata", library("koin.ksp.compiler"))
                add("kspAndroid", platform(library("koin.annotations.bom")))
                add("kspAndroid", library("koin.ksp.compiler"))
            }
        }

        Unit
    }
}