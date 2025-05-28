package ro.mihaiblaga.jetlagtool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ro.mihaiblaga.jetlagtool.core.di.AppModule
import ro.mihaiblaga.jetlagtool.presentation.home.HomeView
import ro.mihaiblaga.jetlagtool.ui.theme.JetlagToolTheme

class MainActivity : ComponentActivity() {

    private lateinit var appModule: AppModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appModule = (application as JetlagTool).appModule

        enableEdgeToEdge()

        setContent {
            JetlagToolTheme {

                Scaffold(
                    modifier = Modifier,
                ) { innerPadding ->
                    HomeView(
                        mapViewModel = appModule.mapViewModel,
                        modifier = Modifier
                            .consumeWindowInsets(innerPadding)
                    )
                }


            }
        }
    }
}