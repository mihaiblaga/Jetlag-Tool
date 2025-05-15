package ro.mihaiblaga.jetlagtool.ui

import DashboardButton
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ro.mihaiblaga.jetlagtool.MapState
import ro.mihaiblaga.jetlagtool.ui.map.MapLibreView


@Composable
fun MainUI(
    state: MapState,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        MapLibreView(
            modifier = Modifier
                .fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DashboardButton(onClick = { Log.d("DashboardButton", "Clicked") })
        }
    }
}

@Preview
@Composable
fun MainUIPreview() {
    val sampleMapState = MapState()
    MainUI(
        state=sampleMapState,
        modifier = Modifier
            .fillMaxSize()
    )
}