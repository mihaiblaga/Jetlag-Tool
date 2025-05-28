package ro.mihaiblaga.jetlagtool.domain.model.actions

import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.geojson.Feature

sealed class MapAction {
    data class AnimateCamera(val position: CameraPosition) : MapAction()

    data class DrawFeature(
        val feature: Feature,
        val sourceId: String,
        val layerId: String
    ) : MapAction()

    data class AddMarker(
        val position: LatLng,
        val title: String? = null,
        val markerId: String
    ) : MapAction()

    class ClearSelectedPoints() : MapAction()

}