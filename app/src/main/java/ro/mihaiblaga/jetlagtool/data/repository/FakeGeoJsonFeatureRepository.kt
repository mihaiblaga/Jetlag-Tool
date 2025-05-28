package ro.mihaiblaga.jetlagtool.data.repository

import org.maplibre.geojson.Feature
import ro.mihaiblaga.jetlagtool.domain.geojson.GeoJsonFeatureRepository

class FakeGeoJsonFeatureRepository : GeoJsonFeatureRepository {
    override fun getFeatures(): List<Feature>? {
        TODO("Not yet implemented")
    }
}