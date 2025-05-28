import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ro.mihaiblaga.jetlagtool.data.model.AdministrativeDivision
import ro.mihaiblaga.jetlagtool.presentation.sidebar.SidebarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Sidebar(
    state: StateFlow<SidebarState>,
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
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 10.dp)
        ) {
            items(state.value.items) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .weight(2f),
                        fontSize = 25.sp,
                        text = item.name
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun SidebarPreview() {
    Sidebar(
        state = MutableStateFlow(
            SidebarState(
                items = listOf(
                    AdministrativeDivision(
                        1,
                        "test",
                        "test",
                        1
                    )
                )
            )
        )
    )
}
