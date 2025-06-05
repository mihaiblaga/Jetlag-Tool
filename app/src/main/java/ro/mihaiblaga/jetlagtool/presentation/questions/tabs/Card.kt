package ro.mihaiblaga.jetlagtool.presentation.questions.tabs

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.mihaiblaga.jetlagtool.presentation.questions.Field

@Composable
fun Card(
    modifier: Modifier = Modifier,
    field: Field
) {
    Box(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .border(
                shape = RoundedCornerShape(10.dp),
                width = 1.dp,
                color = MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.5f)
            )
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(vertical = 3.dp),
                text = field.label,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            if (field.description != null) {
                Text(field.description)
            }
        }
    }


}

@Preview
@Composable
fun CardPreview(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        field = Field(
            id = "tree",
            label = "🌳 A Tree",
            description = "Must include the entire tree."
        )
    )

}