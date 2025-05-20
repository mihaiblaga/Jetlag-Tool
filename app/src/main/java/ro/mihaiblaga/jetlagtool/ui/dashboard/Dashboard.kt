import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
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
import ro.mihaiblaga.jetlagtool.ui.dashboard.DashboardButton

@Composable
fun Dashboard(
    modifier: Modifier = Modifier,
    model: MapViewModel,
    ) {
    var isExpanded by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .padding(50.dp)
        ) {
            val polygonPoints: List<LatLng> = listOf(
                LatLng(46.7712, 23.6236),
                LatLng(44.7712, 22.6236),
                LatLng(44.7712, 24.6236)
            )
            AnimatedVisibility(isExpanded) {
                DashboardButton(
                    icon = Icons.Filled.Edit,
                    shape = CircleShape,
                    onClick = {
                        model.requestPolygonDraw(polygonPoints)
                        Log.d("Dashboard", "Polygon drawn")
                    }
                )
            }
            DashboardButton(
                icon = Icons.Filled.Edit,
                shape = CircleShape,
                onClick = {
                    model.requestCameraAnimation(CameraPosition.Builder()
                        .target(LatLng(46.7712, 23.6236))
                        .zoom(12.0)
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