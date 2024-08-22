import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.manga.app.App
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Manga",
    ) {
        App()
    }
}