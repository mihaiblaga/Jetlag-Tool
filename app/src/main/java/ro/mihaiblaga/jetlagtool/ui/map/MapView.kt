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
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView
import ro.mihaiblaga.jetlagtool.BuildConfig
import ro.mihaiblaga.jetlagtool.models.actions.MapAction
import ro.mihaiblaga.jetlagtool.models.SelectionMode
import ro.mihaiblaga.jetlagtool.util.addMarkerToMap
import ro.mihaiblaga.jetlagtool.util.clearMapFeatures
import ro.mihaiblaga.jetlagtool.util.drawPolygon

@Composable
fun MapLibreView(
    model: MapViewModel,
    modifier: Modifier = Modifier,
    onMapReady: (MapLibreMap) -> Unit,
) {
    Log.d(
        "MapLibreView",
        "FUNCTION START."
    )
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val mapView = remember { MapView(context) }

    val actionsToProcess by model.mapActions.collectAsState()

    val currentSelectionMode by model.currentSelectionMode.collectAsState()
    Log.d(
        "MapLibreView",
        "Collected States -- Mode: $currentSelectionMode, Actions: ${actionsToProcess.size}"
    )


    val mapClickListener = remember {
        MapLibreMap.OnMapClickListener { point ->
                Log.d("MapViewModel", "Map clicked at: $point")
                model.addPoint(point)
                if (model.selectedPoints.value.size >= 3) {
                    model.requestPolygonDraw(model.featureFromPoints(model.selectedPoints.value))
                }
            true
        }
    }

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
                val bottomMarginInDp = 16
                val leftMarginInDp = 16
                val bottomMarginInPx = (bottomMarginInDp * density).toInt()
                val leftMarginInPx = (leftMarginInDp * density).toInt()
                uiSettings.setCompassMargins(leftMarginInPx, 0, 0, bottomMarginInPx)
                onMapReady(maplibreMap)
            }
            mapView
        },
        modifier = modifier,
        update = { currentMapView ->
            Log.d(
                "AndroidView",
                "UPDATE block START. Mode: $currentSelectionMode, Actions: ${actionsToProcess.size}"
            )
            currentMapView.getMapAsync { maplibreMap ->
                if (currentSelectionMode is SelectionMode.PointSelectionMode) {
                    maplibreMap.removeOnMapClickListener(mapClickListener)
                    Log.d("MapView", "Trying to remove MapClickListener before adding")
                    maplibreMap.addOnMapClickListener(mapClickListener)
                    Log.d("MapView", "MapClickListener added")
                } else {
                    maplibreMap.removeOnMapClickListener(mapClickListener)
                    Log.d("MapView", "MapClickListener removed")
                }
                maplibreMap.getStyle { style ->
                    val processedActions = mutableListOf<MapAction>()
                    actionsToProcess.forEach { action ->
                        when (action) {
                            is MapAction.AnimateCamera -> {
                                Log.d("MapViewModel", "Received action: $action")
                                maplibreMap.animateCamera(
                                    CameraUpdateFactory.newCameraPosition(
                                        action.position
                                    )
                                )
                            }

                            is MapAction.DrawFeature -> {
                                Log.d("MapViewModel", "Received action: $action")
                                val currentPoints = action.feature
                                Log.d(
                                    "MapDebug",
                                    "Attempting to draw polygon. Points: $currentPoints"
                                )
                                drawPolygon(
                                    style,
                                    action.feature,
                                    action.sourceId,
                                    action.layerId
                                )
                            }

                            is MapAction.AddMarker -> {
                                Log.d("MapViewModel", "Received action: $action")
                                addMarkerToMap(context, style, action.position, action.markerId)
                            }

                            is MapAction.ClearSelectedPoints -> {
                                clearMapFeatures(style)
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
    )
}


