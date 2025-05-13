package ro.mihaiblaga.jetlagtool.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            FloatingActionButton(
                onClick = { },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(75.dp),
                shape = CircleShape,

            ) {
                Icon(
                    Icons.Filled.Edit, "Test"
                )
            }
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