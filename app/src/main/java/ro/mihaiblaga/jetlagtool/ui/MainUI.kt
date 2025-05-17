package ro.mihaiblaga.jetlagtool.ui

import Dashboard
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.maps.MapLibreMap
import ro.mihaiblaga.jetlagtool.MapViewModel
import ro.mihaiblaga.jetlagtool.ui.map.MapLibreView


@Composable
fun MainUI(
    mapViewModel: MapViewModel,
    modifier: Modifier = Modifier,
) {
    var mapLibreInstance by remember { mutableStateOf<MapLibreMap?>(null) }

    val cameraUpdateRequest: CameraPosition? by mapViewModel.cameraUpdateRequest.collectAsState()
    LaunchedEffect(cameraUpdateRequest) {
        cameraUpdateRequest?.let { position ->
            mapLibreInstance?.animateCamera(CameraUpdateFactory.newCameraPosition(position))
            mapViewModel.cameraUpdateHandled()
        }
    }

    Box(modifier = modifier) {
        MapLibreView(
            model = mapViewModel,
            modifier = Modifier
                .fillMaxSize(),
            onMapReady = { mapLibreMap ->
                mapLibreInstance = mapLibreMap
            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Dashboard(
                model = mapViewModel,
            )
        }
    }
}
@Preview
@Composable
fun MainUIPreview() {
    val sampleMapModel = viewModel<MapViewModel>()
    MainUI(
        mapViewModel = sampleMapModel,
        modifier = Modifier
            .fillMaxSize()
    )
}