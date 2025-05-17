package ro.mihaiblaga.jetlagtool

import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng

sealed class MapAction {
    data class AnimateCamera(val position: CameraPosition) : MapAction()

    data class DrawPolygon(val points: List<LatLng>,
                           val sourceId: String = "default-polygon",
                           val layerId: String = "default-polygon-id") : MapAction()

    data class AddMarker(val position: LatLng,
                         val title: String? = null,
                         val snippet: String? = null) : MapAction()

}