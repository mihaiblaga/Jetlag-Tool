import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
    ) {
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier
                .padding(75.dp),
            shape = CircleShape,

            ) {
            Icon(
                Icons.Filled.Edit, "Test"
            )
        }
    }
}