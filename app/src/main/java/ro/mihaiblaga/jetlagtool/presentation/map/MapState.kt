package ro.mihaiblaga.jetlagtool.presentation.map

import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.geojson.Feature

data class MapState(
    val cameraPosition: CameraPosition = CameraPosition.Builder().build(),
    val features: List<Feature>? = emptyList(),
    val currentMapTool: MapTool = MapTool.Regular,
    val selectedPoints: List<LatLng> = emptyList(),
)
