package ro.mihaiblaga.jetlagtool.presentation.home

import Sidebar
import android.util.Log
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ro.mihaiblaga.jetlagtool.presentation.home.bottombar.BottomBar
import ro.mihaiblaga.jetlagtool.presentation.home.map.MapView
import ro.mihaiblaga.jetlagtool.presentation.home.map.MapViewModel
import ro.mihaiblaga.jetlagtool.presentation.home.topbar.TopBar
import ro.mihaiblaga.jetlagtool.presentation.sidebar.SidebarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    mapViewModel: MapViewModel,
    sidebarViewModel: SidebarViewModel,
    modifier: Modifier = Modifier,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            Sidebar(
                modifier = modifier,
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
                    viewModel = mapViewModel
                )
            }
        ) { innerPadding ->
            MapView(
                modifier = modifier
                    .fillMaxSize()
                    .consumeWindowInsets(innerPadding),
                viewModel = mapViewModel
            )
        }
    }


}

@Preview
@Composable
fun HomeViewPreview() {
    HomeScreen(
        mapViewModel = hiltViewModel(),
        sidebarViewModel = hiltViewModel(),
        modifier = Modifier
            .fillMaxSize()
    )
}