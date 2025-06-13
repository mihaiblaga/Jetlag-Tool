package ro.mihaiblaga.jetlagtool.domain.repository

import ro.mihaiblaga.jetlagtool.domain.model.Question

interface QuestionRepository {
    suspend fun getQuestions(): List<Question>

    suspend fun getQuestionById(id: Long): Question?

    suspend fun getQuestionsByCategory(category: String): List<Question>

    suspend fun insertQuestion(question: Question): Long

}