package ro.mihaiblaga.jetlagtool.data.repository

import org.maplibre.geojson.Feature
import ro.mihaiblaga.jetlagtool.domain.repository.FeatureRepository

class FakeFeatureRepository : FeatureRepository {
    override suspend fun getFeatures(): List<Feature>? {
        TODO("Not yet implemented")
    }

    override suspend fun getFeatureById(id: Long): Feature? {
        TODO("Not yet implemented")
    }
}