
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.build_logic.convention.configureKotlinMultiplatform
import com.build_logic.convention.utils.implementation
import com.build_logic.convention.utils.library
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
            apply("com.android.application")
            apply("org.jetbrains.compose")
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        configureKotlinMultiplatform(
            kotlinMultiplatformExtension = extensions.getByType<KotlinMultiplatformExtension>(),
            commonExtension = extensions.getByType<BaseAppModuleExtension>()
        )

        dependencies{
            implementation(library("napier"))
        }
    }
}



