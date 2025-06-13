package ro.mihaiblaga.jetlagtool.presentation.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.maplibre.geojson.Point
import ro.mihaiblaga.jetlagtool.data.repository.FakeFeatureRepository
import ro.mihaiblaga.jetlagtool.domain.repository.FeatureRepository
import ro.mihaiblaga.jetlagtool.util.createCircleFeatureFromTwoPoints
import ro.mihaiblaga.jetlagtool.util.createLineStringFeature
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val featureRepository: FeatureRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MapState())

    val state: StateFlow<MapState> = _state

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.AnimateCamera -> {
                _state.value = _state.value.copy(
                    cameraPosition = event.position
                )
                Log.d("MapViewModel", "Animated camera to: ${event.position}")
            }

            is MapEvent.AddMarker -> TODO()
            is MapEvent.ClearMap -> {
                _state.value = _state.value.copy(
                    features = emptyList(),
                    selectedPoints = emptyList()
                )
                Log.d("MapViewModel", "Cleared map")
            }

            is MapEvent.DrawFeature -> TODO()
            is MapEvent.DrawFeatures -> {
                viewModelScope.launch {
                    val features = featureRepository.getFeatures()
                    _state.update {
                        it.copy(features = features)
                    }
                    Log.d("MapViewModel", "Drawn features: ${features?.size}")
                }
            }

            is MapEvent.ClearSelectedPoints -> TODO()
            is MapEvent.ChangeTool -> {
                _state.value = _state.value.copy(
                    currentMapTool = event.mapTool,
                    selectedPoints = emptyList()
                )
                Log.d("MapViewModel", "Changed tool to: ${event.mapTool}")
            }

            is MapEvent.AddPoint -> {
                _state.value = _state.value.copy(
                    selectedPoints = _state.value.selectedPoints + event.point
                )
                handlePoints()
                Log.d("MapViewModel", "Added point: ${event.point}")
            }
        }
    }

    fun handlePoints() {
        when (_state.value.currentMapTool) {
            MapTool.Circle -> {
                when (_state.value.selectedPoints.size) {
                    0, 1 -> {
                        Log.d("MapViewModel", "Not enough points to draw circle")
                    }

                    else -> {
                        val centerPoint = _state.value.selectedPoints[0]
                        val pointOnCircumference = _state.value.selectedPoints[1]
                        val circle = createCircleFeatureFromTwoPoints(
                            Point.fromLngLat(centerPoint.longitude, centerPoint.latitude),
                            Point.fromLngLat(
                                pointOnCircumference.longitude,
                                pointOnCircumference.latitude
                            )
                        )
                        if (circle != null) {
                            _state.update {
                                it.copy(
                                    features = it.features?.plus(circle),
                                    selectedPoints = emptyList()
                                )
                            }
                        }
                        Log.d("MapViewModel", "Drawn circle: $circle")
                    }
                }
            }

            MapTool.Line -> {
                when (_state.value.selectedPoints.size) {
                    0, 1 -> {
                        Log.d("MapViewModel", "Not enough points to draw line")
                    }

                    else -> {
                        val lineString = createLineStringFeature(_state.value.selectedPoints)
                        if (lineString != null) {
                            _state.update {
                                it.copy(
                                    features = it.features?.plus(lineString),
                                )
                            }
                        }
                        Log.d("MapViewModel", "Drawn line: $lineString")
                    }
                }
            }

            MapTool.Point -> TODO()
            MapTool.Regular -> TODO()
        }
    }
}

val fakeMapViewModel = MapViewModel(FakeFeatureRepository())