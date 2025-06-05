package ro.mihaiblaga.jetlagtool.presentation.questions.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ro.mihaiblaga.jetlagtool.presentation.questions.GameModes
import ro.mihaiblaga.jetlagtool.presentation.questions.QuestionType

@Preview
@Composable
fun QuestionsTab(
    modifier: Modifier = Modifier,
    mode: QuestionType = GameModes.PHOTOS_MODE
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Column() {
            TabHeader(questionType = mode)
            LazyColumn {
                items(mode.categories) { category ->
                    Row() {
                        Column() {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 20.dp)
                                    .padding(top = 8.dp),
                                color = MaterialTheme.colorScheme.primary,
                                text = category.title
                            )
                            category.fields.forEach { field ->
                                Card(
                                    modifier
                                        .padding(vertical = 8.dp), field
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}