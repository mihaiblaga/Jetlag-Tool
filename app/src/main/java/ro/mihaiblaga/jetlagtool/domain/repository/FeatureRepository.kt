package ro.mihaiblaga.jetlagtool.domain.repository

import org.maplibre.geojson.Feature

interface FeatureRepository {

    suspend fun getFeatures(): List<Feature>?

    suspend fun getFeatureById(id: Long): Feature?

}