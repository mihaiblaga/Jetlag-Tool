package ro.mihaiblaga.jetlagtool.presentation.questions.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.mihaiblaga.jetlagtool.domain.model.Category

@Preview(showBackground = true)
@Composable
fun TabHeader(
    modifier: Modifier = Modifier,
    category: Category = Category.MEASURING
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 20.dp),
                    text = category.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Cost: ")


//                Text(questionType.cost)
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                Text("Time: ")
//                Text(questionType.time)
            }
        }
    }
}