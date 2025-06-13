package ro.mihaiblaga.jetlagtool.presentation.home.bottombar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LinearScale
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.twotone.Circle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ro.mihaiblaga.jetlagtool.presentation.map.MapEvent
import ro.mihaiblaga.jetlagtool.presentation.map.MapTool
import ro.mihaiblaga.jetlagtool.presentation.map.MapViewModel
import ro.mihaiblaga.jetlagtool.presentation.map.fakeMapViewModel

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel
) {
    var isExpanded by remember { mutableStateOf(true) }

    val mapState = viewModel.state.collectAsState()

    BottomAppBar(
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 50.dp),
        containerColor = Color.Transparent
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.weight(1f))
            AnimatedVisibility(
                visible = isExpanded,
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 4.dp),
                enter = slideInHorizontally(
                    initialOffsetX = { it / 2 }
                ) + scaleIn(),
                exit = slideOutHorizontally(
                    targetOffsetX = { it / 2 }
                ) + scaleOut()
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FloatingActionButton(
                        onClick = {
                            viewModel.onEvent(MapEvent.ClearMap)
                        },
                        shape = CircleShape
                    ) {
                        Icon(Icons.Filled.Delete, "Delete")
                    }
                    FloatingActionButton(
                        modifier = Modifier
                            .then(
                                if (mapState.value.currentMapTool == MapTool.Circle)
                                    Modifier
                                        .border(
                                            width = 2.dp,
                                            color = MaterialTheme.colorScheme.surfaceTint,
                                            shape = CircleShape
                                        )
                                else Modifier
                            ),
                        onClick = {
                            if (mapState.value.currentMapTool != MapTool.Circle) {
                                viewModel.onEvent(MapEvent.ChangeTool(MapTool.Circle))
                            } else {
                                viewModel.onEvent(MapEvent.ChangeTool(MapTool.Regular))
                            }
                        },
                        shape = CircleShape
                    ) {
                        Icon(Icons.TwoTone.Circle, "Circle Tool")
                    }
                    FloatingActionButton(
                        modifier = Modifier
                            .then(
                                if (mapState.value.currentMapTool == MapTool.Line)
                                    Modifier
                                        .border(
                                            width = 2.dp,
                                            color = MaterialTheme.colorScheme.surfaceTint,
                                            shape = CircleShape
                                        )
                                else Modifier
                            ),
                        onClick = {
                            if (mapState.value.currentMapTool != MapTool.Line) {
                                viewModel.onEvent(MapEvent.ChangeTool(MapTool.Line))
                            } else {
                                viewModel.onEvent(MapEvent.ChangeTool(MapTool.Regular))
                            }
                        },
                        shape = CircleShape
                    ) {
                        Icon(Icons.Filled.LinearScale, "Line Tool")
                    }
                }

            }
            Icon(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .padding(top = 6.dp),
                imageVector = if (isExpanded) Icons.AutoMirrored.Filled.ArrowRight else Icons.AutoMirrored.Filled.ArrowLeft,
                tint = MaterialTheme.colorScheme.surfaceTint,
                contentDescription = "arrow"
            )
            FloatingActionButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier
                    .padding(vertical = 14.dp, horizontal = 8.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
@Preview
fun BottomBarPreview() {
    BottomBar(
        viewModel = fakeMapViewModel
    )
}