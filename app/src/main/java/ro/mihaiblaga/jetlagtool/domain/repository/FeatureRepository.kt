package ro.mihaiblaga.jetlagtool.domain.repository

import org.maplibre.geojson.Feature

interface FeatureRepository {

    fun getFeatures(): List<Feature>?

}