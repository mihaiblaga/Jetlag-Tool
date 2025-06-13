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
import ro.mihaiblaga.jetlagtool.domain.model.Category
import ro.mihaiblaga.jetlagtool.domain.model.GameSize
import ro.mihaiblaga.jetlagtool.domain.model.Question

@Composable
fun Card(
    modifier: Modifier = Modifier,
    question: Question
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
                text = question.text,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            if (question.description != null) {
                Text(question.description)
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
        question = Question(
            id = 1,
            text = "🌳 A Tree",
            description = "Must include the entire tree.",
            category = Category.PHOTOGRAPHIC,
            type = "Test",
            gameSizes = listOf(GameSize.SMALL, GameSize.MEDIUM, GameSize.LARGE),
            cost = "Draw 3 cards",
            time = "5 minutes",
            distance = 123
        )
    )

}