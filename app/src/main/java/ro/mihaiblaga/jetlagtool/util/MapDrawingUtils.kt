package ro.mihaiblaga.jetlagtool.util

import androidx.core.graphics.toColorInt
import com.google.gson.JsonObject
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.Style
import org.maplibre.android.style.layers.CircleLayer
import org.maplibre.android.style.layers.FillLayer
import org.maplibre.android.style.layers.LineLayer
import org.maplibre.android.style.layers.PropertyFactory.circleColor
import org.maplibre.android.style.layers.PropertyFactory.circleOpacity
import org.maplibre.android.style.layers.PropertyFactory.fillColor
import org.maplibre.android.style.layers.PropertyFactory.fillOpacity
import org.maplibre.android.style.layers.PropertyFactory.lineColor
import org.maplibre.android.style.layers.PropertyFactory.lineWidth
import org.maplibre.android.style.sources.GeoJsonSource
import org.maplibre.geojson.Feature
import org.maplibre.geojson.FeatureCollection
import org.maplibre.geojson.LineString
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

fun createLineStringFeature(points: List<LatLng>): Feature? {
    val points = points.map { Point.fromLngLat(it.longitude, it.latitude) }
    return Feature.fromGeometry(LineString.fromLngLats(points))
}

fun initializeSourcesLayers(style: Style) {
    val pointSourceId = "feature-point-source"
    val pointLayerId = "feature-point-layer"
    val pointLayer = CircleLayer(pointLayerId, pointSourceId)
    pointLayer.setProperties(circleColor("#80FF0000".toColorInt()), circleOpacity(0.5f))
    style.addSource(GeoJsonSource(pointSourceId))
    style.addLayer(pointLayer)

    val lineSourceId = "feature-line-source"
    val lineLayerId = "feature-line-layer"
    val lineLayer = LineLayer(lineLayerId, lineSourceId)
    lineLayer.setProperties(lineWidth(3f), lineColor("#000FF0".toColorInt()))
    style.addSource(GeoJsonSource(lineSourceId))
    style.addLayer(lineLayer)

    val polygonSourceId = "feature-polygon-source"
    val polygonLayerId = "feature-polygon-layer"
    val fillLayer = FillLayer(polygonLayerId, polygonSourceId)
    fillLayer.setProperties(fillColor("#80FF0000".toColorInt()), fillOpacity(0.5f))
    style.addSource(GeoJsonSource(polygonSourceId))
    style.addLayer(fillLayer)
}