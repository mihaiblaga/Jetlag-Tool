package ro.mihaiblaga.jetlagtool.presentation.home.map

import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.geojson.Feature

sealed class MapEvent {
    data class AnimateCamera(val position: CameraPosition) : MapEvent()

    data class DrawFeature(
        val feature: Feature
    ) : MapEvent()

    data class AddMarker(
        val position: LatLng,
        val title: String? = null,
        val markerId: String
    ) : MapEvent()

    object DrawFeatures : MapEvent()

    object ClearSelectedPoints : MapEvent()

    object ClearMap : MapEvent()

    data class ChangeTool(val tool: Tool) : MapEvent()

    data class AddPoint(val point: LatLng) : MapEvent()


}