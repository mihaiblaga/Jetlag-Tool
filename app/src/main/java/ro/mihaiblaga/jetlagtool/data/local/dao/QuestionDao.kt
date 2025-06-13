package ro.mihaiblaga.jetlagtool.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ro.mihaiblaga.jetlagtool.data.local.DatabaseSchema
import ro.mihaiblaga.jetlagtool.data.local.entity.QuestionEntity

@Dao
interface QuestionDao {
    @Insert
    suspend fun insert(questionEntity: QuestionEntity): Long

    @Query("SELECT * FROM ${DatabaseSchema.TABLE_QUESTION} WHERE id = :id")
    suspend fun getQuestionById(id: Long): QuestionEntity?

    @Query("SELECT * FROM ${DatabaseSchema.TABLE_QUESTION}")
    suspend fun getAllQuestions(): List<QuestionEntity>

    @Query("SELECT * FROM ${DatabaseSchema.TABLE_QUESTION} WHERE category = :category")
    suspend fun getQuestionsByCategory(category: String): List<QuestionEntity>

}