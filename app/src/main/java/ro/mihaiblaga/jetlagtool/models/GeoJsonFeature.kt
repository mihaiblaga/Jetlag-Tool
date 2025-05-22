package ro.mihaiblaga.jetlagtool.models

data class GeoJsonFeature(
    val name: String,
    val coordinates: List<List<List<Double>>>
)
