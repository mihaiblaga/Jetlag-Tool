package ro.mihaiblaga.jetlagtool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import org.maplibre.android.MapLibre
import org.maplibre.android.WellKnownTileServer
import ro.mihaiblaga.jetlagtool.ui.MainUI
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

        setContent {
            JetlagToolTheme {
                val viewModel = viewModel<MapViewModel>()
                MainUI(
                    mapViewModel = viewModel,
                    modifier = Modifier
                )

            }
        }
    }
}