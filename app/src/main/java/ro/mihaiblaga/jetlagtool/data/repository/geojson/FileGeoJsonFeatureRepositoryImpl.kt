package ro.mihaiblaga.jetlagtool.data.repository.geojson

import android.util.Log
import kotlinx.serialization.json.Json
import ro.mihaiblaga.jetlagtool.models.GeoJsonFeature
import ro.mihaiblaga.jetlagtool.models.GeoJsonFeatureCollection

class FileGeoJsonFeatureRepositoryImpl : GeoJsonFeatureRepository {

    private var featureCollection: GeoJsonFeatureCollection? = null
    private var features = mutableListOf<GeoJsonFeature>()

    fun loadFromJson(json: String) {
        featureCollection = Json.decodeFromString(json)
        Log.d("FileGeoJsonFeatureRepository", "Loaded ${features.size} features from JSON")
    }

    override fun getFeatures(): List<GeoJsonFeature> {
        return featureCollection?.features ?: emptyList()
    }
}