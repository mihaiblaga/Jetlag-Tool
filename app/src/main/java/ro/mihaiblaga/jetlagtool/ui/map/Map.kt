package ro.mihaiblaga.jetlagtool.ui.map


import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView
import ro.mihaiblaga.jetlagtool.BuildConfig
import ro.mihaiblaga.jetlagtool.MapViewModel

@Composable
fun MapLibreView(
    model: MapViewModel,
    modifier: Modifier = Modifier,
    onMapReady: (MapLibreMap) -> Unit
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val mapView = remember { MapView(context) }

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
    AndroidView(factory = {
        mapView.onCreate(null)
        mapView.getMapAsync { maplibreMap ->
            maplibreMap.setStyle("https://api.maptiler.com/maps/openstreetmap/style.json?key=${BuildConfig.MAPLIBRE_ACCESS_TOKEN}")

            val uiSettings = maplibreMap.uiSettings
            val density = context.resources.displayMetrics.density
            val bottomMarginInDp = 16 // Example margin in dp
            val leftMarginInDp = 16 // Example margin in dp
            val bottomMarginInPx = (bottomMarginInDp * density).toInt()
            val leftMarginInPx = (leftMarginInDp * density).toInt()
            uiSettings.setCompassMargins(leftMarginInPx, 0, 0, bottomMarginInPx)
            onMapReady(maplibreMap)
        }
        mapView
    },
        modifier = modifier)
}

@Preview
@Composable
fun MapViewPreview() {
    MapLibreView(
        model = TODO(),
        modifier = TODO(),
        onMapReady = {}
    )
}
