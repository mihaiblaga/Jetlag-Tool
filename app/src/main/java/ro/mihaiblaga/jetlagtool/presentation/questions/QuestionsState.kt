package ro.mihaiblaga.jetlagtool.presentation.questions

import ro.mihaiblaga.jetlagtool.domain.model.Category
import ro.mihaiblaga.jetlagtool.domain.model.Question
import kotlin.enums.EnumEntries

data class QuestionsState(
    val isDropdownExpanded: Boolean = false,
    val questions: List<Question> = emptyList(),
    val selectedCategory: Category = Category.MEASURING,
    val categories: EnumEntries<Category> = Category.entries

)