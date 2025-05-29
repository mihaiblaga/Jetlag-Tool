package ro.mihaiblaga.jetlagtool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import ro.mihaiblaga.jetlagtool.presentation.home.HomeView
import ro.mihaiblaga.jetlagtool.ui.theme.JetlagToolTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            JetlagToolTheme {

                Scaffold(
                    modifier = Modifier,
                ) { innerPadding ->
                    HomeView(
                        mapViewModel = hiltViewModel(),
                        sidebarViewModel = hiltViewModel(),
                        modifier = Modifier
                            .consumeWindowInsets(innerPadding)
                    )
                }


            }
        }
    }
}