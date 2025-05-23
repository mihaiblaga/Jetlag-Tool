package ro.mihaiblaga.jetlagtool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import org.maplibre.android.MapLibre
import org.maplibre.android.WellKnownTileServer
import ro.mihaiblaga.jetlagtool.ui.home.MainUI
import ro.mihaiblaga.jetlagtool.ui.map.MapViewModel
import ro.mihaiblaga.jetlagtool.ui.theme.JetlagToolTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val mapLibreApiKey = BuildConfig.MAPLIBRE_ACCESS_TOKEN
        MapLibre.getInstance(
            this,
            mapLibreApiKey,
            WellKnownTileServer.MapTiler,
        )
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            JetlagToolTheme {
                val viewModel = viewModel<MapViewModel>()

                Scaffold(
                    modifier = Modifier,
                ) { innerPadding ->
                    MainUI(
                        mapViewModel = viewModel,
                        modifier = Modifier
                            .consumeWindowInsets(innerPadding)
                    )
                }


            }
        }
    }
}