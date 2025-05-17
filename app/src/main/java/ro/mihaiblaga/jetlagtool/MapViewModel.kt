package ro.mihaiblaga.jetlagtool

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng

class MapViewModel : ViewModel() {

    private val _mapActions = MutableStateFlow<List<MapAction>>(emptyList())
    val mapActions: StateFlow<List<MapAction>> = _mapActions.asStateFlow()

    fun requestCameraAnimation(position: CameraPosition) {
        _mapActions.update { currentActions -> currentActions + MapAction.AnimateCamera(position) }
    }

    fun requestPolygonDraw(
        points: List<LatLng>,
        sourceId: String = "default-polygon-${System.currentTimeMillis()}",
        layerId: String = "default-polygon-id-${System.currentTimeMillis()}"
    ) {
        _mapActions.update { currentActions ->
            currentActions + MapAction.DrawPolygon(points, sourceId, layerId)
        }
    }

    fun requestAddMarker(position: LatLng, title: String? = null, snippet: String? = null) {
        _mapActions.update { currentActions -> currentActions + MapAction.AddMarker(position, title, snippet)}
    }

    fun mapActionHandled(actionToRemove: MapAction) {
        _mapActions.update { currentActions -> currentActions - actionToRemove }
    }

    fun allMapActionsHandled(processedActions: List<MapAction>) {
        _mapActions.update { currentActions ->
            currentActions.filterNot { it in processedActions }
        }
    }
    fun clearAllMapActions() {
        _mapActions.update { emptyList() }
    }
}