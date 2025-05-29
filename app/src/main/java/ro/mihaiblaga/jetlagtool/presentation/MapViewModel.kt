package ro.mihaiblaga.jetlagtool.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.geojson.Feature
import org.maplibre.geojson.Point
import org.maplibre.geojson.Polygon
import ro.mihaiblaga.jetlagtool.domain.repository.FeatureRepository
import ro.mihaiblaga.jetlagtool.presentation.home.map.MapAction
import ro.mihaiblaga.jetlagtool.presentation.home.map.SelectionMode
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val featureRepository: FeatureRepository
) : ViewModel() {

    private val _mapActions = MutableStateFlow<List<MapAction>>(emptyList())

    val mapActions: StateFlow<List<MapAction>> = _mapActions.asStateFlow()


    private val _currentSelectionMode =
        MutableStateFlow<SelectionMode>(SelectionMode.RegularSelectionMode)

    val currentSelectionMode: StateFlow<SelectionMode> = _currentSelectionMode.asStateFlow()

    private val _selectedPoints = MutableStateFlow<List<LatLng>>(emptyList())
    val selectedPoints: StateFlow<List<LatLng>> = _selectedPoints.asStateFlow()

    fun requestCameraAnimation(position: CameraPosition) {
        val newAction = MapAction.AnimateCamera(position)
        Log.d(
            "MapViewModel",
            "requestCameraAnimation called. Old actions size: ${_mapActions.value.size}"
        )
        _mapActions.update { currentActions -> currentActions + newAction }
        Log.d("MapViewModel", "_mapActions updated. New size: ${_mapActions.value.size}")
    }

    fun setSelectionMode(newMode: SelectionMode) {
        Log.d(
            "MapViewModel",
            "setSelectionMode called. New mode: $newMode. Current mode: ${_currentSelectionMode.value}"
        )
        if (_currentSelectionMode.value == newMode) {
            Log.d("MapViewModel", "Mode is already $newMode. NOT updating.")
            return
        }
        _currentSelectionMode.value = newMode
        Log.d("MapViewModel", "_currentSelectionMode updated to: ${_currentSelectionMode.value}")
    }

    fun requestPolygonDraw(
        feature: Feature,
        sourceId: String = "polygon-source-${UUID.randomUUID()}",
        layerId: String = "polygon-layer-${UUID.randomUUID()}"
    ) {
        _mapActions.update { currentActions ->
            currentActions + MapAction.DrawFeature(feature, sourceId, layerId)
        }
    }

    fun drawFeatures() {
        viewModelScope.launch(Dispatchers.IO) {
            val features = featureRepository.getFeatures()
            if (features != null) {
                for (feature in features) {
                    requestPolygonDraw(feature)
                }
            }

        }
    }

    fun addPoint(latLng: LatLng) {
        _selectedPoints.update { currentPoints -> currentPoints + latLng }
        _mapActions.update { currentActions ->
            currentActions + MapAction.AddMarker(
                latLng,
                markerId = "polygon-point-${UUID.randomUUID()}"
            )
        }
        Log.d("MapViewModel", "Point added to polygon: $latLng")
    }

    fun featureFromPoints(points: List<LatLng>): Feature {
        val polygonCoordinates = mutableListOf<List<Point>>()
        val exteriorRing = mutableListOf<Point>()

        for (point in points) {
            exteriorRing.add(Point.fromLngLat(point.longitude, point.latitude))
        }
        if (exteriorRing.isNotEmpty() && exteriorRing.first() != exteriorRing.last()) {
            exteriorRing.add(exteriorRing.first())
        }

        polygonCoordinates.add(exteriorRing)
        val polygonGeometry = Polygon.fromLngLats(polygonCoordinates)
        val feature = Feature.fromGeometry(polygonGeometry)
        return feature
    }

    fun clearSelectedPoints() {
        _selectedPoints.update { emptyList() }
        _mapActions.update { currentActions ->
            currentActions + MapAction.ClearSelectedPoints()
        }
    }

    fun requestAddMarker(
        position: LatLng,
        title: String? = null,
        markerId: String = "marker-${UUID.randomUUID()}"
    ) {
        _mapActions.update { currentActions ->
            currentActions + MapAction.AddMarker(
                position,
                title,
                markerId
            )
        }
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