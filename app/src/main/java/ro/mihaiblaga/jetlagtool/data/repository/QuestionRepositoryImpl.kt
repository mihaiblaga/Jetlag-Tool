package ro.mihaiblaga.jetlagtool.data.repository

import ro.mihaiblaga.jetlagtool.data.local.dao.QuestionDao
import ro.mihaiblaga.jetlagtool.data.local.entity.QuestionEntity
import ro.mihaiblaga.jetlagtool.domain.model.Question
import ro.mihaiblaga.jetlagtool.domain.repository.QuestionRepository

class QuestionRepositoryImpl(
    private val dao: QuestionDao
) : QuestionRepository {
    override suspend fun getQuestions(): List<Question> {
        return dao.getAllQuestions().map { it.toQuestion() }
    }

    override suspend fun getQuestionById(id: Long): Question? {
        return dao.getQuestionById(id)?.toQuestion()
    }

    override suspend fun getQuestionsByCategory(category: String): List<Question> {
        return dao.getQuestionsByCategory(category).map { it.toQuestion() }
    }

    override suspend fun insertQuestion(question: Question): Long {
        return dao.insert(toEntity(question))
    }

    private fun toEntity(question: Question): QuestionEntity {
        return QuestionEntity(
            id = question.id,
            category = question.category,
            type = question.type,
            gameSizes = question.gameSizes,
            cost = question.cost,
            time = question.time,
            distance = question.distance,
            text = question.text,
            description = question.description
        )
    }

}