package app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import app.dashboard.DashboardScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.ksp.generated.defaultModule

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinApplication({ defaultModule() }) {
            MangaApp()
        }
    }
}

@Composable
fun MangaApp() {
    DashboardScreen()
}

