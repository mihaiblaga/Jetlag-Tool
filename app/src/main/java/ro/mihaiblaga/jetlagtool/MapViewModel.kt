package ro.mihaiblaga.jetlagtool

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MapViewModel: ViewModel() {
    var state by mutableStateOf(MapState())
        private set
}