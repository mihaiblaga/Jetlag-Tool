package ro.mihaiblaga.jetlagtool

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.Style

class MapViewModel: ViewModel() {
    private val _cameraUpdateRequest = MutableStateFlow<CameraPosition?>(null)

    private val _createPolygonRequest = MutableStateFlow<Style?>(null)

    val cameraUpdateRequest: StateFlow<CameraPosition?> = _cameraUpdateRequest.asStateFlow()

    val createPolygonRequest: StateFlow<Style?> = _createPolygonRequest.asStateFlow()

    private fun createPolygonRequest() {
        _createPolygonRequest.value = Style.Builder().fromUri(Style.MAPBOX_STREETS).build()
    }

    fun requestMapCenter(longitude: Double, latitude: Double, zoom: Double) {
        _cameraUpdateRequest.value = CameraPosition.Builder()
            .target(LatLng(longitude, latitude))
            .zoom(zoom)
            .build()
    }
    fun cameraUpdateHandled() {
        _cameraUpdateRequest.value = null
    }
}