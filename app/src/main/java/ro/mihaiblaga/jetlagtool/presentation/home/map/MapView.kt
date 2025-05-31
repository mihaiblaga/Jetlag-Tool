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
import androidx.core.graphics.toColorInt
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.maplibre.android.MapLibre
import org.maplibre.android.WellKnownTileServer
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style
import org.maplibre.android.style.layers.CircleLayer
import org.maplibre.android.style.layers.FillLayer
import org.maplibre.android.style.layers.LineLayer
import org.maplibre.android.style.layers.PropertyFactory.fillColor
import org.maplibre.android.style.layers.PropertyFactory.fillOpacity
import org.maplibre.android.style.layers.PropertyFactory.lineColor
import org.maplibre.android.style.layers.PropertyFactory.lineWidth
import org.maplibre.android.style.sources.GeoJsonSource
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

    val mapClickListener = remember {
        MapLibreMap.OnMapClickListener { point ->
            Log.d("MapViewModel", "Map clicked at: $point")
            viewModel.onEvent(MapEvent.AddPoint(point))
            true
        }
    }



    AndroidView(
        factory = {
            val styleUrl = BuildConfig.STYLE_URL
            mapView.onCreate(null)
            mapView.getMapAsync { map ->
                map.setStyle(styleUrl)
                map.getStyle { style ->
                    initializeSourcesLayers(style)
                }
            }
            mapView
        },
        modifier = modifier,
        update = { mapView ->
            Log.d("MapView", "Updating map view")
            val currentFeatures = mapState.features
            val currentCameraPosition = mapState.cameraPosition
            mapView.getMapAsync { map ->
                if (mapState.currentTool != Tool.Regular) {
                    map.removeOnMapClickListener(mapClickListener)
                    Log.d("MapView", "Trying to remove MapClickListener before adding")
                    map.addOnMapClickListener(mapClickListener)
                    Log.d("MapView", "MapClickListener added")
                } else {
                    map.removeOnMapClickListener(mapClickListener)
                    Log.d("MapView", "MapClickListener removed")
                }
                map.getStyle { style ->
                    drawFeatures(style, currentFeatures)
                    currentCameraPosition.let { position ->
                        if (map.cameraPosition != currentCameraPosition) {
                            map.cameraPosition = position
                        }
                    }
                }
            }
        }
    )
}

fun initializeSourcesLayers(style: Style) {
    val pointSourceId = "feature-point-source"
    val pointLayerId = "feature-point-layer"
    val pointLayer = CircleLayer(pointLayerId, pointSourceId)
    pointLayer.setProperties(fillColor("#80FF0000".toColorInt()), fillOpacity(0.5f))
    style.addSource(GeoJsonSource(pointSourceId))
    style.addLayer(pointLayer)

    val lineSourceId = "feature-line-source"
    val lineLayerId = "feature-line-layer"
    val lineLayer = LineLayer(lineLayerId, lineSourceId)
    lineLayer.setProperties(lineWidth(3f), lineColor("#000FF0".toColorInt()))
    style.addSource(GeoJsonSource(lineSourceId))
    style.addLayer(lineLayer)

    val polygonSourceId = "feature-polygon-source"
    val polygonLayerId = "feature-polygon-layer"
    val fillLayer = FillLayer(polygonLayerId, polygonSourceId)
    fillLayer.setProperties(fillColor("#80FF0000".toColorInt()), fillOpacity(0.5f))
    style.addSource(GeoJsonSource(polygonSourceId))
    style.addLayer(fillLayer)
}