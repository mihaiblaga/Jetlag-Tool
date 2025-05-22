package ro.mihaiblaga.jetlagtool.ui

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import org.maplibre.android.maps.MapLibreMap
import ro.mihaiblaga.jetlagtool.ui.bottombar.BottomBar
import ro.mihaiblaga.jetlagtool.ui.map.MapLibreView
import ro.mihaiblaga.jetlagtool.ui.map.MapViewModel
import ro.mihaiblaga.jetlagtool.ui.topbar.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUI(
    mapViewModel: MapViewModel,
    modifier: Modifier = Modifier,
) {
    var mapLibreInstance by remember { mutableStateOf<MapLibreMap?>(null) }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                modifier = modifier,
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            BottomBar(
                modifier = modifier,
                model = mapViewModel
            )
        }
    ) { innerPadding ->
        MapLibreView(
            model = mapViewModel,
            modifier = modifier
                .consumeWindowInsets(innerPadding),
            onMapReady = { mapLibreMap ->
                mapLibreInstance = mapLibreMap
            })
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