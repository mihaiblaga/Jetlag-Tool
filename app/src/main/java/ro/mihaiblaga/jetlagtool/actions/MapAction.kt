package ro.mihaiblaga.jetlagtool.actions

import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng

sealed class MapAction {
    data class AnimateCamera(val position: CameraPosition) : MapAction()

    data class DrawPolygon(
        val points: List<LatLng>,
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