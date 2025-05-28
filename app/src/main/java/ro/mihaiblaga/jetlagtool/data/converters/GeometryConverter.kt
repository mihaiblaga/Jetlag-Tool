package ro.mihaiblaga.jetlagtool.data.converters

import androidx.room.TypeConverter
import org.maplibre.geojson.Geometry
import org.maplibre.geojson.LineString
import org.maplibre.geojson.MultiPolygon
import org.maplibre.geojson.Point
import org.maplibre.geojson.Polygon
import ro.mihaiblaga.jetlagtool.data.local.entity.FeatureEntity

class GeometryConverter {
    @TypeConverter
    fun fromGeometryEntity(featureEntity: FeatureEntity): Geometry {
        return when (featureEntity.type) {
            "Point" -> Point.fromJson(featureEntity.coordinatesJson ?: "")
            "LineString" -> LineString.fromJson(featureEntity.coordinatesJson ?: "")
            "Polygon" -> Polygon.fromJson(featureEntity.coordinatesJson ?: "")
            "MultiPolygon" -> MultiPolygon.fromJson(featureEntity.coordinatesJson)
            else -> throw IllegalArgumentException("Unsupported geometry type: ${featureEntity.type}")
        }
    }
}