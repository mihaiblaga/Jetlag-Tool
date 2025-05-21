package ro.mihaiblaga.jetlagtool.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import org.maplibre.android.maps.MapLibreMap
import ro.mihaiblaga.jetlagtool.ui.dashboard.Dashboard
import ro.mihaiblaga.jetlagtool.ui.map.MapLibreView
import ro.mihaiblaga.jetlagtool.ui.map.MapViewModel


@Composable
fun MainUI(
    mapViewModel: MapViewModel,
    modifier: Modifier = Modifier,
) {
    var mapLibreInstance by remember { mutableStateOf<MapLibreMap?>(null) }

    Box(modifier = modifier) {
        MapLibreView(
            model = mapViewModel,
            modifier = Modifier
                .fillMaxSize(),
            onMapReady = { mapLibreMap ->
                mapLibreInstance = mapLibreMap
            }
        )
        Log.d("MainUI", "Called MapLibreView")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd),
            horizontalAlignment = Alignment.End,
        ) {
            Dashboard(
                model = mapViewModel,
            )
            Log.d("Dashboard", "Called Dashboard")

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