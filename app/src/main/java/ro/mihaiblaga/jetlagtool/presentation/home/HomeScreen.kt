package ro.mihaiblaga.jetlagtool.presentation.home

import Sidebar
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ro.mihaiblaga.jetlagtool.presentation.home.bottombar.BottomBar
import ro.mihaiblaga.jetlagtool.presentation.home.topbar.TopBar
import ro.mihaiblaga.jetlagtool.presentation.map.MapView
import ro.mihaiblaga.jetlagtool.presentation.map.MapViewModel
import ro.mihaiblaga.jetlagtool.presentation.map.fakeMapViewModel
import ro.mihaiblaga.jetlagtool.presentation.sidebar.SidebarViewModel
import ro.mihaiblaga.jetlagtool.presentation.sidebar.fakeSidebarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    mapViewModel: MapViewModel,
    sidebarViewModel: SidebarViewModel,
    modifier: Modifier = Modifier,
) {
    val isInPreview = LocalInspectionMode.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            Sidebar(
                modifier = modifier
                    .fillMaxHeight(),
                viewModel = sidebarViewModel,
                onCloseButtonClicked = {
                    scope.launch {
                        drawerState.close()
                    }
                }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopBar(
                    modifier = Modifier.fillMaxWidth(),
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
                    modifier = Modifier.fillMaxWidth(),
                    viewModel = mapViewModel
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .consumeWindowInsets(innerPadding)
            ) {
                if (isInPreview) {
                    PreviewPlaceholder(
                        modifier = modifier,
                        text = "MapLibre Map Area"
                    )
                } else {
                    MapView(
                        modifier = modifier,
                        viewModel = mapViewModel
                    )
                }
            }
        }
    }


}

@Composable
fun PreviewPlaceholder(modifier: Modifier = Modifier, text: String) {
    Box(
        modifier = modifier
            .background(Color.LightGray)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}

@Preview
@Composable
fun HomeViewPreview() {
    HomeScreen(
        mapViewModel = fakeMapViewModel,
        sidebarViewModel = fakeSidebarViewModel,
        modifier = Modifier
            .fillMaxSize()
    )
}