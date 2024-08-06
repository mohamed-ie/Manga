package app.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.dashboard.navigation.DashboardNavHost

@Composable
fun DashboardScreen(dashboardState: DashboardState = rememberDashboardState()) {
    DashboardNavHost(modifier = Modifier.fillMaxSize(), dashboardState)
}