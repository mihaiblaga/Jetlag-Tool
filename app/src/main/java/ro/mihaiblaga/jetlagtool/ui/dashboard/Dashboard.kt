package ro.mihaiblaga.jetlagtool.ui.dashboard

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import ro.mihaiblaga.jetlagtool.MapViewModel
import ro.mihaiblaga.jetlagtool.ui.SelectionMode

@Composable
fun Dashboard(
    modifier: Modifier = Modifier,
    model: MapViewModel,
) {
    var isExpanded by remember { mutableStateOf(false) }

    val selectionMode by model.currentSelectionMode.collectAsState()

    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(50.dp)
        ) {
            DashboardButton(
                icon = Icons.Filled.Edit,
                shape = CircleShape,
                onClick = {
                    Log.d("ToggleButton", "Toggle Selection Mode BUTTON CLICKED")
                    if (selectionMode is SelectionMode.PointSelectionMode) {
                        model.setSelectionMode(SelectionMode.RegularSelectionMode())
                    } else {
                        model.setSelectionMode(SelectionMode.PointSelectionMode())
                    }
                }
            )
            DashboardButton(
                icon = Icons.Filled.Edit,
                shape = CircleShape,
                onClick = {
                    Log.d("ZoomButton", "Animate Camera BUTTON CLICKED.")
                    model.requestCameraAnimation(
                        CameraPosition.Builder()
                            .target(LatLng(46.7712, 23.6236))
                            .zoom(12.0)
                            .bearing(0.0)
                            .build()
                    )
                    isExpanded = !isExpanded
                    Log.d("Dashboard", "DashboardButton clicked. State is: $isExpanded")
                }
            )
        }
    }
}

@Preview
@Composable
fun DashboardPreview() {
    val viewModel = viewModel<MapViewModel>()
    Dashboard(
        modifier = Modifier,
        model = viewModel,
    )
}