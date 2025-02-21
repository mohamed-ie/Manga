import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.manga.app.App
import com.manga.app.di.AppModule
import org.koin.compose.KoinApplication

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Manga",
    ) {
        KoinApplication({ modules(AppModule().module) }) { App() }
    }
}