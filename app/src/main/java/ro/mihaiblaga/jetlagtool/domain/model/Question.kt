package ro.mihaiblaga.jetlagtool.domain.model

data class Question(
    val id: Long,
    val category: Category,
    val type: String,
    val gameSizes: List<GameSize>,
    val cost: String,
    val time: String,
    val distance: Int?,
    val text: String,
    val description: String?
)