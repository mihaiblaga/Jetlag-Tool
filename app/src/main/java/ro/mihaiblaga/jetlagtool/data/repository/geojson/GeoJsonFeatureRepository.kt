package ro.mihaiblaga.jetlagtool.data.repository.geojson

import org.maplibre.geojson.Feature

interface GeoJsonFeatureRepository {

    fun getFeatures(): List<Feature>?

}