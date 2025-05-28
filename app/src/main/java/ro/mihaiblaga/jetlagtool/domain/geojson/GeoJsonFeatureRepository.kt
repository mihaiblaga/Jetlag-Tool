package ro.mihaiblaga.jetlagtool.domain.geojson

import org.maplibre.geojson.Feature

interface GeoJsonFeatureRepository {

    fun getFeatures(): List<Feature>?

}