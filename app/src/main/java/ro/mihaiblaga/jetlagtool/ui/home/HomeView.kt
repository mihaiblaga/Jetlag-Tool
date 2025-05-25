package ro.mihaiblaga.jetlagtool.ui.home

import android.util.Log
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.maplibre.android.maps.MapLibreMap
import ro.mihaiblaga.jetlagtool.ui.bottombar.BottomBar
import ro.mihaiblaga.jetlagtool.ui.map.MapLibreView
import ro.mihaiblaga.jetlagtool.ui.map.MapViewModel
import ro.mihaiblaga.jetlagtool.ui.topbar.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    mapViewModel: MapViewModel,
    modifier: Modifier = Modifier,
) {
    var mapLibreInstance by remember { mutableStateOf<MapLibreMap?>(null) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(),
            ) {
                Text("Drawer")
            }
        }
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopBar(
                    modifier = modifier,
                    scrollBehavior = scrollBehavior,
                    onNavigationIconClicked = {
                        Log.d("MainUI", "Navigation icon clicked.")
                        scope.launch {
                            drawerState.open()
                        }
                    }
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
                    .fillMaxSize()
                    .consumeWindowInsets(innerPadding),
                onMapReady = { mapLibreMap ->
                    mapLibreInstance = mapLibreMap
                })
        }
    }


}

@Preview
@Composable
fun HomeViewPreview() {
    val sampleMapModel = viewModel<MapViewModel>()
    HomeView(
        mapViewModel = sampleMapModel,
        modifier = Modifier
            .fillMaxSize()
    )
}