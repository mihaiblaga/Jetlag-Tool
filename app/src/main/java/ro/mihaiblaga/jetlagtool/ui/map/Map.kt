package ro.mihaiblaga.jetlagtool.ui.map


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
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style
import org.maplibre.android.style.layers.FillLayer
import org.maplibre.android.style.layers.PropertyFactory.fillColor
import org.maplibre.android.style.layers.PropertyFactory.fillOpacity
import org.maplibre.android.style.sources.GeoJsonSource
import org.maplibre.geojson.Point
import org.maplibre.geojson.Polygon
import ro.mihaiblaga.jetlagtool.BuildConfig
import ro.mihaiblaga.jetlagtool.MapAction
import ro.mihaiblaga.jetlagtool.MapViewModel

@Composable
fun MapLibreView(
    model: MapViewModel,
    modifier: Modifier = Modifier,
    onMapReady: (MapLibreMap) -> Unit,
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val mapView = remember { MapView(context) }

    val actionsToProcess by model.mapActions.collectAsState()

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
        modifier = modifier,
        update = { currentMapView ->
            if (actionsToProcess.isNotEmpty()) {
                currentMapView.getMapAsync { maplibreMap ->
                    maplibreMap.getStyle { style ->
                        val processedActions = mutableListOf<MapAction>()
                        actionsToProcess.forEach { action ->
                            when (action) {
                                is MapAction.AnimateCamera -> {
                                    Log.d("MapViewModel", "Received AnimateCamera action: $action")
                                    maplibreMap.animateCamera(CameraUpdateFactory.newCameraPosition(action.position))
                                }

                                is MapAction.DrawPolygon -> {
                                    Log.d("MapViewModel", "Received DrawPolygon action: $action")
                                    val currentPoints = action.points
                                    Log.d("MapDebug", "Attempting to draw polygon. Points: $currentPoints")
                                    drawPolygonOnMap(style, action.points, action.sourceId, action.layerId)
                                }

                                is MapAction.AddMarker -> {
                                    Log.d("MapViewModel", "Received DrawPolygon action: $action")
                                }
                            }
                            processedActions.add(action)
                        }
                        if (processedActions.isNotEmpty()) {
                            model.allMapActionsHandled(processedActions)
                        }
                    }
                }
            }
        }
    )
}


fun drawPolygonOnMap(
    style: Style,
    points: List<LatLng?>,
    sourceId: String = "",
    layerId: String = ""
): Boolean {
    Log.d("DrawFuncDebug", "Function called. SourceID: $sourceId, LayerID: $layerId, Points: $points")

    if (points.isEmpty()) {
        Log.w("DrawFuncDebug", "Not enough points. Removing old layers if any.")
        style.removeSource(sourceId)
        style.removeLayer(layerId)
        return false
    }
    val polygonCoordinates = mutableListOf<List<Point>>()
    val exteriorRing = mutableListOf<Point>()

    for (point in points) {
        exteriorRing.add(Point.fromLngLat(point!!.longitude, point.latitude))
    }
    Log.d("DrawFuncDebug", "Exterior ring (GeoJSON points): $exteriorRing")
    if (exteriorRing.isNotEmpty() && exteriorRing.first() != exteriorRing.last()) {
        exteriorRing.add(exteriorRing.first())
        Log.d("DrawFuncDebug", "Closed the ring.")
    }
    polygonCoordinates.add(exteriorRing)
    val polygonGeometry = try {
        Polygon.fromLngLats(polygonCoordinates)
    } catch (e: IllegalStateException) {
        Log.e("MapViewModel", "Error creating polygon geometry", e)
        return false
    }
    Log.d("DrawFuncDebug", "Polygon Geometry created: ${polygonGeometry.toJson()}")
    style.removeSource(sourceId)
    style.removeLayer(layerId)

    try {
        val geoJsonSource = GeoJsonSource(sourceId, polygonGeometry)
        style.addSource(geoJsonSource)
        Log.d("DrawFuncDebug", "GeoJsonSource '$sourceId' ADDED.")
        
        val fillLayer = FillLayer(layerId, sourceId)
        fillLayer.setProperties(
            fillColor("#80FF0000".toColorInt()),
            fillOpacity(0.5f)
        )
        style.addLayer(fillLayer)
        Log.d("DrawFuncDebug", "FillLayer '$layerId' ADDED.")
        Log.d("MapViewModel", "Polygon drawn successfully")
        return true
    } catch (e: Exception) {
        Log.e("MapViewModel", "Error drawing polygon", e)
        return false
    }
}