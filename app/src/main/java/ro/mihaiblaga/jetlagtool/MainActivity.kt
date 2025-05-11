package ro.mihaiblaga.jetlagtool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.maplibre.android.MapLibre
import org.maplibre.android.WellKnownTileServer
import ro.mihaiblaga.jetlagtool.ui.map.MapLibreView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapLibreApiKey = BuildConfig.MAPLIBRE_ACCESS_TOKEN
        MapLibre.getInstance(
            this,
            mapLibreApiKey,
            WellKnownTileServer.MapTiler,
        )

        setContent {
            MapLibreView()
        }
    }
}