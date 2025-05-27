package ro.mihaiblaga.jetlagtool.data.repository.geojson

import android.util.Log
import org.maplibre.geojson.Feature
import org.maplibre.geojson.FeatureCollection

class FileGeoJsonFeatureRepositoryImpl : GeoJsonFeatureRepository {

    private lateinit var featureCollection: FeatureCollection

    fun loadFromJson(json: String) {
        featureCollection = FeatureCollection.fromJson(json)
        Log.d(
            "FileGeoJsonFeatureRepository",
            "Loaded ${featureCollection.features()?.size} features from JSON"
        )
    }

    override fun getFeatures(): List<Feature>? {
        return featureCollection.features()
    }
}