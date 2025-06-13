package ro.mihaiblaga.jetlagtool.presentation.questions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ro.mihaiblaga.jetlagtool.domain.model.Category
import ro.mihaiblaga.jetlagtool.domain.model.Question
import ro.mihaiblaga.jetlagtool.domain.repository.QuestionRepository
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val questionRepository: QuestionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(QuestionsState())
    val state: StateFlow<QuestionsState> = _state
    var questions: List<Question> = emptyList()

    init {
        viewModelScope.launch {
            questions = questionRepository.getQuestions()

            _state.value = _state.value.copy(
                questions = questions
            )
        }
    }

    fun toggleQuestionsDropdown() {
        _state.value = _state.value.copy(
            isDropdownExpanded = !_state.value.isDropdownExpanded
        )
    }

    fun setCategory(category: Category) {
        _state.value = _state.value.copy(
            selectedCategory = category
        )
    }
}