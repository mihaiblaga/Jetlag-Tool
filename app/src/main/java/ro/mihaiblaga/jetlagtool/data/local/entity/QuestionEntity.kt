package ro.mihaiblaga.jetlagtool.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ro.mihaiblaga.jetlagtool.data.local.DatabaseSchema
import ro.mihaiblaga.jetlagtool.domain.model.Category
import ro.mihaiblaga.jetlagtool.domain.model.GameSize
import ro.mihaiblaga.jetlagtool.domain.model.Question

@Entity(tableName = DatabaseSchema.TABLE_QUESTION)
data class QuestionEntity(
    @ColumnInfo(name = COLUMN_CATEGORY)
    val category: Category,

    @ColumnInfo(name = COLUMN_TYPE)
    val type: String,

    @ColumnInfo(name = COLUMN_GAME_SIZES)
    val gameSizes: List<GameSize>,

    @ColumnInfo(name = COLUMN_COST)
    val cost: String,

    @ColumnInfo(name = COLUMN_TIME)
    val time: String,

    @ColumnInfo(name = COLUMN_DISTANCE)
    val distance: Int?,

    @ColumnInfo(name = COLUMN_TEXT)
    val text: String,

    @ColumnInfo(name = COLUMN_DESCRIPTION)
    val description: String?,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long = 0
) {
    constructor(question: Question) : this(
        category = question.category,
        type = question.type,
        gameSizes = question.gameSizes,
        cost = question.cost,
        time = question.time,
        distance = question.distance,
        text = question.text,
        description = question.description
    )

    companion object {
        const val COLUMN_ID = "id"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_GAME_SIZES = "gameSizes"
        const val COLUMN_TYPE = "type"
        const val COLUMN_COST = "cost"
        const val COLUMN_TIME = "time"
        const val COLUMN_DISTANCE = "distance"
        const val COLUMN_TEXT = "text"
        const val COLUMN_DESCRIPTION = "description"

    }

    fun toQuestion(): Question {
        return Question(
            id = id,
            category = category,
            type = type,
            gameSizes = gameSizes,
            cost = cost,
            time = time,
            distance = distance,
            text = text,
            description = description
        )
    }
}
