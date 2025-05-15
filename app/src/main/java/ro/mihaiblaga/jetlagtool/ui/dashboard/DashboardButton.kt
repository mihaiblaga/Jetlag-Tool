package ro.mihaiblaga.jetlagtool.ui.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun DashboardButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    shape: Shape,
    onClick: () -> Unit,
) {
    Box() {
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier
                .padding(10.dp)
                .then(modifier),
            shape = shape,

            ) {
            Icon(
                icon, "Test"
            )
        }
    }
}