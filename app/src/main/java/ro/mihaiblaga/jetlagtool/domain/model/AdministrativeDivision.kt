package ro.mihaiblaga.jetlagtool.domain.model

data class AdministrativeDivision(
    val id: Long = 0,
    val level: Int,
    val type: String,
    val name: String,
    val geometry: BorderGeometry
)