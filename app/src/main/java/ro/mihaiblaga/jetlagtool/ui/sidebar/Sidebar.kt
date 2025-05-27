import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun Sidebar(
    modifier: Modifier = Modifier,
    onCloseButtonClicked: () -> Unit = {}
) {
    ModalDrawerSheet(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 10.dp)
                .padding(top = 35.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Sidebar", fontSize = 25.sp)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = onCloseButtonClicked,
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close Sidebar",
                )
            }
        }
        HorizontalDivider()
        NavigationDrawerItem(
            label = { Text(text = "Clues") },
            selected = false,
            onClick = {}
        )
    }
}

