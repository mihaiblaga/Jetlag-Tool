package ro.mihaiblaga.jetlagtool.util

import androidx.core.graphics.toColorInt
import com.google.gson.JsonObject
import org.maplibre.android.maps.Style
import org.maplibre.android.style.layers.CircleLayer
import org.maplibre.android.style.layers.FillLayer
import org.maplibre.android.style.layers.Layer
import org.maplibre.android.style.layers.LineLayer
import org.maplibre.android.style.layers.PropertyFactory.circleColor
import org.maplibre.android.style.layers.PropertyFactory.circleRadius
import org.maplibre.android.style.layers.PropertyFactory.fillColor
import org.maplibre.android.style.layers.PropertyFactory.fillOpacity
import org.maplibre.android.style.layers.PropertyFactory.lineColor
import org.maplibre.android.style.layers.PropertyFactory.lineWidth
import org.maplibre.android.style.sources.GeoJsonSource
import org.maplibre.geojson.Feature
import org.maplibre.geojson.Point
import org.maplibre.turf.TurfConstants
import org.maplibre.turf.TurfMeasurement
import org.maplibre.turf.TurfTransformation
import java.util.UUID


fun drawFeatures(style: Style, features: List<Feature>?) {
    val sources = style.sources
    val layers = style.layers

    for (source in sources) {
        if (source.id.startsWith("feature")) {
            style.removeSource(source.id)
        }
    }
    for (layer in layers) {
        if (layer.id.startsWith("feature")) {
            style.removeLayer(layer.id)
        }
    }

    if (features != null) {
        for (feature in features) {
            drawFeature(style, feature)
        }
    }
}

fun drawFeature(style: Style, feature: Feature) {
    val geometryType = feature.geometry()?.type()
    val sourceId = "feature-$geometryType-source-${UUID.randomUUID()}"

    val layer = getLayerByFeatureType(geometryType!!, sourceId)

    style.addSource(GeoJsonSource(sourceId, feature))
    style.addLayer(layer)
}

fun getLayerByFeatureType(featureType: String, sourceId: String): Layer {
    val layer: Layer
    when (featureType) {
        "Point" -> {
            layer = CircleLayer("feature-point-layer-${UUID.randomUUID()}", sourceId)
            layer.setProperties(circleRadius(10f), circleColor("#FF0000".toColorInt()))
        }

        "MultiPoint" -> {
            layer = CircleLayer("feature-point-layer-${UUID.randomUUID()}", sourceId)
            layer.setProperties(circleRadius(10f), circleColor("#FF0000".toColorInt()))
        }

        "LineString" -> {
            layer = LineLayer("feature-line-layer-${UUID.randomUUID()}", sourceId)
            layer.setProperties(lineWidth(3f), lineColor("#000FF0".toColorInt()))
        }

        "MultiLineString" -> {
            layer = LineLayer("feature-line-layer-${UUID.randomUUID()}", sourceId)
            layer.setProperties(lineWidth(3f), lineColor("#000FF0".toColorInt()))
        }

        "Polygon" -> {
            layer = FillLayer("feature-polygon-layer-${UUID.randomUUID()}", sourceId)
            layer.setProperties(fillColor("#80FF0000".toColorInt()), fillOpacity(0.5f))
        }

        "MultiPolygon" -> {
            layer = FillLayer("feature-polygon-layer-${UUID.randomUUID()}", sourceId)
            layer.setProperties(fillColor("#80FF0000".toColorInt()), fillOpacity(0.5f))

        }

        else -> throw IllegalArgumentException("Unsupported feature type: $featureType")
    }
    return layer
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