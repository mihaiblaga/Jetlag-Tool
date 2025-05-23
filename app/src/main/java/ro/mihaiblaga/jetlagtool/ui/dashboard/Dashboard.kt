package ro.mihaiblaga.jetlagtool.ui.dashboard

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
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
import ro.mihaiblaga.jetlagtool.models.SelectionMode
import ro.mihaiblaga.jetlagtool.ui.map.MapViewModel

@Composable
fun Dashboard(
    modifier: Modifier = Modifier,
    model: MapViewModel,
) {
    var isExpanded by remember { mutableStateOf(false) }

    val selectionMode by model.currentSelectionMode.collectAsState()

    var checked by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)

            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Edit",
                    modifier = Modifier
                        .padding(10.dp)

                )
                Switch(
                    modifier = Modifier,
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                        Log.d("ToggleButton", "Toggle Selection Mode BUTTON CLICKED")
                        if (selectionMode is SelectionMode.PointSelectionMode) {
                            model.setSelectionMode(SelectionMode.RegularSelectionMode)
                        } else {
                            model.setSelectionMode(SelectionMode.PointSelectionMode)
                        }
                    }
                )
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier
                        .padding(10.dp)

                )
            }
            DashboardButton(
                icon = Icons.Filled.Delete,
                shape = CircleShape,
                onClick = {
                    Log.d("DeleteButton", "DeleteButton BUTTON CLICKED.")
                    model.clearSelectedPoints()
                    isExpanded = !isExpanded
                    Log.d("DeleteButton", "DashboardButton clicked. State is: $isExpanded")
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