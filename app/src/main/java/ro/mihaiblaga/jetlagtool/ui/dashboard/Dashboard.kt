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
import ro.mihaiblaga.jetlagtool.ui.dashboard.DashboardButton

@Composable
fun Dashboard(
    modifier: Modifier = Modifier,
    ) {
    var isExpanded by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .padding(75.dp)
        ) {
            AnimatedVisibility(isExpanded) {
                DashboardButton(
                    icon = Icons.Filled.Edit,
                    shape = CircleShape,

                    ) {
                }
            }
            DashboardButton(
                icon = Icons.Filled.Edit,
                shape = CircleShape,
                onClick = {
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
    Dashboard()
}