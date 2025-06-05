package ro.mihaiblaga.jetlagtool.presentation.questions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.mihaiblaga.jetlagtool.presentation.questions.tabs.QuestionsTab

@Preview
@Composable
fun QuestionsScreen(modifier: Modifier = Modifier) {
    var selectedQuestionType by remember { mutableStateOf<QuestionType>(GameModes.PHOTOS_MODE) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Questions", fontSize = 25.sp, color = MaterialTheme.colorScheme.primary)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column() {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        onClick = { isDropdownExpanded = !isDropdownExpanded },
                    ) {
                        Text(selectedQuestionType.title)
                        if (!isDropdownExpanded) {
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = null
                            )
                        } else {
                            Icon(imageVector = Icons.Filled.ArrowDropUp, contentDescription = null)
                        }
                    }
                    AnimatedVisibility(
                        modifier = Modifier,
                        visible = isDropdownExpanded
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 10.dp),
                        ) {
                            ALL_GAME_MODES_ORDERED.forEach {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    onClick = {
                                        selectedQuestionType = it
                                        isDropdownExpanded = false
                                    },
                                ) {
                                    Text(it.title)
                                }
                            }
                        }
                    }
                    QuestionsTab(modifier = Modifier, selectedQuestionType)
                }
            }
        }
    }
}