package ro.mihaiblaga.jetlagtool.ui.topbar

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    onNavigationIconClicked: () -> Unit
) {
    TopAppBar(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 55.dp)
            .clip(RoundedCornerShape(100.dp)),
        scrollBehavior = scrollBehavior,
        windowInsets = WindowInsets(top = 0.dp),
        colors = TopAppBarDefaults.topAppBarColors(

            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = Color.Transparent
        ),

        title = {},
        navigationIcon = {
            FloatingActionButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp)),
                onClick = onNavigationIconClicked,
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier
                        .padding(start =4.dp, end = 4.dp)
                )
            }

        }

    )
}