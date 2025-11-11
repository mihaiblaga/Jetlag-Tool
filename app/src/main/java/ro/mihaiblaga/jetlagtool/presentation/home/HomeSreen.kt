package ro.mihaiblaga.jetlagtool.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    Box(
        modifier
            .fillMaxSize()
    ) {
        HomeNavGraph(
            navController
        )
    }
}