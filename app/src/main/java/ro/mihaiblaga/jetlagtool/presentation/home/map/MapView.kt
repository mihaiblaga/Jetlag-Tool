package ro.mihaiblaga.jetlagtool.presentation.home.map

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.maplibre.android.MapLibre
import org.maplibre.android.WellKnownTileServer
import org.maplibre.android.maps.MapView
import ro.mihaiblaga.jetlagtool.BuildConfig
import ro.mihaiblaga.jetlagtool.util.drawFeatures


@Composable
fun MapView(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel,
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val mapLibreApiKey = BuildConfig.MAPLIBRE_ACCESS_TOKEN

    MapLibre.getInstance(
        context,
        mapLibreApiKey,
        WellKnownTileServer.MapTiler,
    )

    val mapView = remember { MapView(context) }
    val mapState by viewModel.state.collectAsState()

    DisposableEffect(lifecycle) {

        val observer = object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) = mapView.onResume()
            override fun onPause(owner: LifecycleOwner) = mapView.onPause()
            override fun onDestroy(owner: LifecycleOwner) = mapView.onDestroy()
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }


    AndroidView(
        factory = {
            val styleUrl =
                "https://api.maptiler.com/maps/openstreetmap/style.json?key=${BuildConfig.MAPLIBRE_ACCESS_TOKEN}"
            mapView.onCreate(null)
            mapView.getMapAsync { map ->
                map.setStyle(styleUrl)

            }
            mapView
        },
        modifier = modifier,
        update = { mapView ->
            Log.d("MapView", "Updating map view")
            val currentFeatures = mapState.features
            mapView.getMapAsync { map ->
                map.getStyle { style ->
                    drawFeatures(style, currentFeatures)
                    mapState.cameraPosition.let { position ->
                        map.cameraPosition = position
                    }
                }
            }
        }
    )
}
