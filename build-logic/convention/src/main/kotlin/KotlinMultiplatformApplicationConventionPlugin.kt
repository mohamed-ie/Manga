import com.build_logic.convention.configureKotlinAndroidApplication
import com.build_logic.convention.configureKotlinMultiplatform
import com.build_logic.convention.utils.version
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.desktop.DesktopExtension
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

class KotlinMultiplatformApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
            apply("com.android.application")
            apply("org.jetbrains.compose")
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        configureKotlinMultiplatform()

        configureDesktop()

        configureKotlinAndroidApplication()
    }
}

private fun Project.configureDesktop() = extensions.getByType<ComposeExtension>()
    .extensions
    .getByType<DesktopExtension>()
    .apply {
        application {
            nativeDistributions {
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                packageVersion = version("versionName")
            }
        }
    }
