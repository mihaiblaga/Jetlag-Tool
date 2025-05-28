package ro.mihaiblaga.jetlagtool.domain.model

import org.maplibre.geojson.Geometry

data class BorderGeometry(
    val id: Long = 0,
    val geometryType: String,
    val geometry: Geometry
)