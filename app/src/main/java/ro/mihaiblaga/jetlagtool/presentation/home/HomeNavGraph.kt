package ro.mihaiblaga.jetlagtool.presentation.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ro.mihaiblaga.jetlagtool.presentation.home.util.Screens
import ro.mihaiblaga.jetlagtool.presentation.questions.QuestionsScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Questions.route
    ) {
        composable(route = Screens.Map.route) {
            MapScreen(viewModel = hiltViewModel())
        }
        composable(route = Screens.Questions.route) {
            QuestionsScreen(viewModel = hiltViewModel(), navController = navController)
        }
    }
}