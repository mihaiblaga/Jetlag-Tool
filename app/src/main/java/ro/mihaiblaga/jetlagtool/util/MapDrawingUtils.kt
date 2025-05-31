package ro.mihaiblaga.jetlagtool.util

import com.google.gson.JsonObject
import org.maplibre.android.maps.Style
import org.maplibre.android.style.sources.GeoJsonSource
import org.maplibre.geojson.Feature
import org.maplibre.geojson.FeatureCollection
import org.maplibre.geojson.Point
import org.maplibre.turf.TurfConstants
import org.maplibre.turf.TurfMeasurement
import org.maplibre.turf.TurfTransformation


fun drawFeatures(style: Style, features: List<Feature>?) {
    val pointSource = style.getSourceAs<GeoJsonSource>("feature-point-source")
    val lineSource = style.getSourceAs<GeoJsonSource>("feature-line-source")
    val polygonSource = style.getSourceAs<GeoJsonSource>("feature-polygon-source")

    val pointFeatures = mutableListOf<Feature>()
    val lineFeatures = mutableListOf<Feature>()
    val polygonFeatures = mutableListOf<Feature>()

    for (feature in features!!) {
        when (feature.geometry()?.type()) {
            "Point", "MultiPoint" -> pointFeatures.add(feature)
            "LineString", "MultiLineString" -> lineFeatures.add(feature)
            "Polygon", "MultiPolygon" -> polygonFeatures.add(feature)
            else -> throw IllegalArgumentException(
                "Unsupported feature type: ${
                    feature.geometry()?.type()
                }"
            )
        }
    }

    pointSource?.setGeoJson(FeatureCollection.fromFeatures(pointFeatures))
    lineSource?.setGeoJson(FeatureCollection.fromFeatures(lineFeatures))
    polygonSource?.setGeoJson(FeatureCollection.fromFeatures(polygonFeatures))
}

fun createCircleFeatureFromTwoPoints(
    centerPoint: Point,
    pointOnCircumference: Point,
    steps: Int = 64,
    properties: JsonObject? = null
): Feature? {
    val radius = TurfMeasurement.distance(
        centerPoint,
        pointOnCircumference,
    )
    if (radius == 0.0) {
        return null
    }

    val circle =
        TurfTransformation.circle(centerPoint, radius, steps, TurfConstants.UNIT_KILOMETERS)

    return Feature.fromGeometry(circle, properties)
}