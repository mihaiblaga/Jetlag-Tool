package ro.mihaiblaga.jetlagtool.util

import android.content.Context
import android.util.Log
import androidx.core.graphics.toColorInt
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.Style
import org.maplibre.android.style.layers.CircleLayer
import org.maplibre.android.style.layers.FillLayer
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.layers.PropertyFactory.fillColor
import org.maplibre.android.style.layers.PropertyFactory.fillOpacity
import org.maplibre.android.style.sources.GeoJsonSource
import org.maplibre.geojson.Feature
import org.maplibre.geojson.Point
import org.maplibre.geojson.Polygon

const val MARKER_SOURCE_ID_PREFIX = "marker-source-"
const val MARKER_LAYER_ID_PREFIX = "marker-layer-"

fun drawPolygonOnMap(
    style: Style,
    points: List<LatLng?>,
    sourceId: String = "",
    layerId: String = ""
): Boolean {
    Log.d(
        "MapDrawingUtils",
        "Function called. SourceID: $sourceId, LayerID: $layerId, Points: $points"
    )

    if (points.isEmpty()) {
        Log.w("MapDrawingUtils", "Not enough points. Removing old layers if any.")
        style.removeSource(sourceId)
        style.removeLayer(layerId)
        return false
    }
    val polygonCoordinates = mutableListOf<List<Point>>()
    val exteriorRing = mutableListOf<Point>()

    for (point in points) {
        exteriorRing.add(Point.fromLngLat(point!!.longitude, point.latitude))
    }
    Log.d("MapDrawingUtils", "Exterior ring (GeoJSON points): $exteriorRing")
    if (exteriorRing.isNotEmpty() && exteriorRing.first() != exteriorRing.last()) {
        exteriorRing.add(exteriorRing.first())
        Log.d("MapDrawingUtils", "Closed the ring.")
    }
    polygonCoordinates.add(exteriorRing)
    val polygonGeometry = try {
        Polygon.fromLngLats(polygonCoordinates)
    } catch (e: IllegalStateException) {
        Log.e("MapDrawingUtils", "Error creating polygon geometry", e)
        return false
    }
    Log.d("MapDrawingUtils", "Polygon Geometry created: ${polygonGeometry.toJson()}")
    style.removeSource(sourceId)
    style.removeLayer(layerId)

    try {
        val geoJsonSource = GeoJsonSource(sourceId, polygonGeometry)
        style.addSource(geoJsonSource)
        Log.d("MapDrawingUtils", "GeoJsonSource '$sourceId' ADDED.")

        val fillLayer = FillLayer(layerId, sourceId)
        fillLayer.setProperties(
            fillColor("#80FF0000".toColorInt()),
            fillOpacity(0.5f)
        )
        style.addLayer(fillLayer)
        Log.d("MapDrawingUtils", "FillLayer '$layerId' ADDED.")
        Log.d("MapDrawingUtils", "Polygon drawn successfully")
        return true
    } catch (e: Exception) {
        Log.e("MapDrawingUtils", "Error drawing polygon", e)
        return false
    }
}

fun addMarkerToMap(
    context: Context,
    style: Style,
    position: LatLng,
    markerId: String,
) {
    if (!style.isFullyLoaded) {
        Log.w("MapDrawingUtils", "Style is not fully loaded. Cannot add marker.")
        return
    }
    val sourceId = "$MARKER_SOURCE_ID_PREFIX$markerId"
    val layerId = "$MARKER_LAYER_ID_PREFIX$markerId"

    style.removeSource(sourceId)
    style.removeLayer(layerId)

    val pointGeometry = Point.fromLngLat(position.longitude, position.latitude)
    val feature = Feature.fromGeometry(pointGeometry)
    val geoJsonSource = GeoJsonSource(sourceId, feature)

    style.addSource(geoJsonSource)

    val circleLayer = CircleLayer(layerId, sourceId)

    circleLayer.setProperties(
        PropertyFactory.circleRadius(10f),
        PropertyFactory.circleColor("#FF0000".toColorInt())
    )
    style.addLayer(circleLayer)
    Log.d("MapDrawingUtils", "Marker added to map")
}

fun clearMapFeatures(style: Style) {
    if (!style.isFullyLoaded) {
        Log.w("MapDrawingUtils", "Style is not fully loaded. Cannot clear features.")
        return
    }
    val layersToRemove = style.layers.filter {
        it.id.startsWith(MARKER_LAYER_ID_PREFIX) || it.id.startsWith("polygon-layer-")
    }

    val sourcesToRemove = style.sources.filter {
        it.id.startsWith(MARKER_SOURCE_ID_PREFIX) || it.id.startsWith("polygon-source-")
    }
    layersToRemove.forEach {
        style.removeLayer(it); Log.d(
        "MapDrawingUtils",
        "Layer removed: ${it.id}"
    )
    }
    sourcesToRemove.forEach {
        style.removeSource(it); Log.d(
        "MapDrawingUtils",
        "Source removed: ${it.id}"
    )
    }
    Log.d("MapDrawingUtils", "Features cleared from map")
}