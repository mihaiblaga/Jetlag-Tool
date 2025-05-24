package ro.mihaiblaga.jetlagtool.models

import kotlinx.serialization.Serializable

@Serializable
data class GeoJsonFeatureCollection(
    val type: String,
    val name: String,
    val features: List<GeoJsonFeature>
)
