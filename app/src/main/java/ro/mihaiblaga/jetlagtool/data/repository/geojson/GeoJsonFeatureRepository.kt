package ro.mihaiblaga.jetlagtool.data.repository.geojson

import ro.mihaiblaga.jetlagtool.models.GeoJsonFeature

interface GeoJsonFeatureRepository {

    fun getFeatures(): List<GeoJsonFeature>

}