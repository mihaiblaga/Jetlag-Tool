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
import androidx.compose.ui.unit.dp
import ro.mihaiblaga.jetlagtool.presentation.questions.QuestionsState

@Composable
fun QuestionsTab(
    modifier: Modifier = Modifier,
    uiState: QuestionsState
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Column() {
            TabHeader(category = uiState.selectedCategory)
            val questionsInCategory =
                uiState.questions.filter { it.category == uiState.selectedCategory }
            LazyColumn {
                val questionsByType = questionsInCategory.groupBy { it.type }
                items(questionsByType.keys.toList()) { type ->
                    Row() {
                        Column() {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 20.dp)
                                    .padding(top = 8.dp),
                                color = MaterialTheme.colorScheme.primary,
                                text = type
                            )
                            questionsByType[type]?.forEach {
                                Card(
                                    modifier.padding(vertical = 8.dp), question = it
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}