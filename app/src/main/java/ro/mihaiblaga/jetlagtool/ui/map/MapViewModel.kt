package ro.mihaiblaga.jetlagtool.ui.map

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import ro.mihaiblaga.jetlagtool.data.repository.geojson.FileGeoJsonFeatureRepositoryImpl
import ro.mihaiblaga.jetlagtool.models.SelectionMode
import ro.mihaiblaga.jetlagtool.models.actions.MapAction
import java.io.IOException
import java.util.UUID

class MapViewModel(application: Application) : AndroidViewModel(application) {

    private val geoJsonRepository = FileGeoJsonFeatureRepositoryImpl()

    private val filePath = "cluj-regions-2016.geojson"

    init {
        Log.d("MapViewModel", "INSTANCE CREATED.")
        loadGeoJsonData()
    }

    private fun loadGeoJsonData() {
        try {
            val context = getApplication<Application>().applicationContext
            val jsonString = context.assets.open(filePath).bufferedReader().use { it.readText() }
            geoJsonRepository.loadFromJson(jsonString)
            Log.d("MapViewModel", "Successfully loaded GeoJSON from $filePath")
        } catch (e: IOException) {
            Log.e("MapViewModel", "Error reading GeoJSON file $filePath from assets", e)
        } catch (e: Exception) {
            Log.e("MapViewModel", "Error processing GeoJSON data from $filePath", e)
        }
    }

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
        points: List<LatLng>,
        sourceId: String = "polygon-source-${UUID.randomUUID()}",
        layerId: String = "polygon-layer-${UUID.randomUUID()}"
    ) {
        _mapActions.update { currentActions ->
            currentActions + MapAction.DrawPolygon(points, sourceId, layerId)
        }
    }

    fun drawFeatures() {
        val features = geoJsonRepository.getFeatures()
        for (feature in features) {
            val geometry = feature.geometry
            val coordinates = geometry.coordinates[0]
            val points = mutableListOf<LatLng>()
            for (coordinate in coordinates) {
                val point = LatLng(coordinate[1], coordinate[0])
                points.add(point)
            }
            requestPolygonDraw(points)
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