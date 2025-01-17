import com.android.build.api.dsl.LibraryExtension
import com.build_logic.convention.configureKotlinAndroid
import com.build_logic.convention.configureKotlinMultiplatform
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class KotlinMultiplatformLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
            apply("com.android.library")
        }

        configureKotlinMultiplatform()

        configureKotlinAndroid(extensions.getByType<LibraryExtension>())
    }
}