package ro.mihaiblaga.jetlagtool.domain.model

import org.maplibre.geojson.Feature

data class AdministrativeDivision(
    val id: Long = 0,
    val level: Int,
    val type: String,
    val name: String,
    val feature: Feature
)