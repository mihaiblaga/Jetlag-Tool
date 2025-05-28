package ro.mihaiblaga.jetlagtool.data.repository

import android.content.Context
import android.util.Log
import org.maplibre.geojson.Feature
import org.maplibre.geojson.FeatureCollection
import ro.mihaiblaga.jetlagtool.domain.geojson.GeoJsonFeatureRepository
import java.io.IOException

class FileGeoJsonFeatureRepositoryImpl(
    val context: Context
) : GeoJsonFeatureRepository {

    private lateinit var featureCollection: FeatureCollection

    init {
        loadFromFile("cluj-regions-2016.geojson")
    }

    fun loadFromFile(path: String) {
        try {
            val jsonString = context.assets.open(path).bufferedReader().use { it.readText() }
            featureCollection = FeatureCollection.fromJson(jsonString)
            Log.d(
                "FileGeoJsonFeatureRepository",
                "Loaded ${featureCollection.features()?.size} features from JSON"
            )
        } catch (e: IOException) {
            Log.e("MapViewModel", "Error reading GeoJSON file $path from assets", e)
        } catch (e: Exception) {
            Log.e("MapViewModel", "Error processing GeoJSON data from $path", e)
        }
    }

    override fun getFeatures(): List<Feature>? {
        return featureCollection.features()
    }
}