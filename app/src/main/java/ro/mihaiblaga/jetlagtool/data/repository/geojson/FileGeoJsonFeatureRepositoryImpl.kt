package ro.mihaiblaga.jetlagtool.data.repository.geojson

import android.util.Log
import org.maplibre.geojson.Feature
import org.maplibre.geojson.FeatureCollection
import ro.mihaiblaga.jetlagtool.models.GeoJsonFeature

class FileGeoJsonFeatureRepositoryImpl : GeoJsonFeatureRepository {

    private lateinit var featureCollection: FeatureCollection
    private var features = mutableListOf<GeoJsonFeature>()

    fun loadFromJson(json: String) {
        featureCollection = FeatureCollection.fromJson(json)
        Log.d("FileGeoJsonFeatureRepository", "Loaded ${features.size} features from JSON")
    }

    override fun getFeatures(): List<Feature>? {
        return featureCollection.features()
    }
}