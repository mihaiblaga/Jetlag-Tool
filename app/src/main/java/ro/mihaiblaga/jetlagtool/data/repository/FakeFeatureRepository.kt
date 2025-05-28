package ro.mihaiblaga.jetlagtool.data.repository

import org.maplibre.geojson.Feature
import ro.mihaiblaga.jetlagtool.domain.repository.FeatureRepository

class FakeFeatureRepository : FeatureRepository {
    override fun getFeatures(): List<Feature>? {
        TODO("Not yet implemented")
    }
}