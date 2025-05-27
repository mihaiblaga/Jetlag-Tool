package ro.mihaiblaga.jetlagtool.data.converters

import androidx.room.TypeConverter
import org.maplibre.geojson.Geometry
import org.maplibre.geojson.LineString
import org.maplibre.geojson.MultiPolygon
import org.maplibre.geojson.Point
import org.maplibre.geojson.Polygon
import ro.mihaiblaga.jetlagtool.data.model.BorderGeometry

class GeometryConverter {
    @TypeConverter
    fun fromGeometryEntity(borderGeometry: BorderGeometry): Geometry {
        return when (borderGeometry.geometryType) {
            "Point" -> Point.fromJson(borderGeometry.coordinatesJson)
            "LineString" -> LineString.fromJson(borderGeometry.coordinatesJson)
            "Polygon" -> Polygon.fromJson(borderGeometry.coordinatesJson)
            "MultiPolygon" -> MultiPolygon.fromJson(borderGeometry.coordinatesJson)
            else -> throw IllegalArgumentException("Unsupported geometry type: ${borderGeometry.geometryType}")
        }
    }
}