package ro.mihaiblaga.jetlagtool.presentation.home.bottombar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ro.mihaiblaga.jetlagtool.data.repository.FakeGeoJsonFeatureRepository
import ro.mihaiblaga.jetlagtool.presentation.MapViewModel
import ro.mihaiblaga.jetlagtool.presentation.MapViewModelFactory

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    model: MapViewModel
) {
    var isExpanded by remember { mutableStateOf(true) }


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
                        onClick = { /* TODO: Action for Camera */ },
                        shape = CircleShape
                    ) {
                        Icon(Icons.Filled.Home, "Home")
                    }
                    FloatingActionButton(
                        onClick = { /* TODO: Action for Edit */ },
                        shape = CircleShape
                    ) {
                        Icon(Icons.Filled.Edit, "Edit")
                    }
                    FloatingActionButton(
                        onClick = { model.drawFeatures() },
                        shape = CircleShape
                    ) {
                        Icon(Icons.Filled.Call, "Call")
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
    val sampleMapModel = MapViewModelFactory(FakeGeoJsonFeatureRepository()).create()
    BottomBar(
        model = sampleMapModel
    )
}