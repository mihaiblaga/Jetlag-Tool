package ro.mihaiblaga.jetlagtool.presentation.home.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : Screens(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home,
    )

    object Map : Screens(
        route = "map",
        title = "Map",
        icon = Icons.Default.Map,
    )

    object Questions : Screens(
        route = "questions",
        title = "Questions",
        icon = Icons.Default.Quiz,
    )
}