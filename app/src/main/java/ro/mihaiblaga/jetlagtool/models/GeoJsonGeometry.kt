package ro.mihaiblaga.jetlagtool.models

import kotlinx.serialization.Serializable

@Serializable
data class GeoJsonGeometry(
    val type: String,
    val coordinates: List<List<List<Double>>>

)
