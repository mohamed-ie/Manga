
import com.android.build.api.dsl.LibraryExtension
import com.build_logic.convention.configureKotlinMultiplatformLibrary
import com.build_logic.convention.utils.library
import com.build_logic.convention.utils.sourceSets
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
            apply("com.android.library")
        }

        val kotlinMultiplatformExtension = extensions.getByType<KotlinMultiplatformExtension>()

        kotlinMultiplatformExtension.apply {
            sourceSets{
                commonMain.dependencies {
                    implementation(library("napier"))
                }
            }
        }

        configureKotlinMultiplatformLibrary(
            kotlinMultiplatformExtension = kotlinMultiplatformExtension,
            commonExtension = extensions.getByType<LibraryExtension>()
        )

    }
}