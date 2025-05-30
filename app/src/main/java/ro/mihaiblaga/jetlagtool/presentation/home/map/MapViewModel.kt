package ro.mihaiblaga.jetlagtool.presentation.home.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ro.mihaiblaga.jetlagtool.domain.repository.FeatureRepository
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
            }

            is MapEvent.AddMarker -> TODO()
            is MapEvent.ClearMap -> {
                _state.value = _state.value.copy(
                    features = emptyList()
                )
            }

            is MapEvent.DrawFeature -> TODO()
            is MapEvent.DrawFeatures -> {
                viewModelScope.launch {
                    val features = featureRepository.getFeatures()
                    _state.update {
                        it.copy(features = features)
                    }
                    Log.d("MapViewModel", "Features drawn: ${features?.size}")
                }
            }

            is MapEvent.ClearSelectedPoints -> TODO()
        }
    }
}