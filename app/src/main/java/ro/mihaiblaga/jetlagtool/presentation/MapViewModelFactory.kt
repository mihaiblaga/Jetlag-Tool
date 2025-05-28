package ro.mihaiblaga.jetlagtool.presentation

import ro.mihaiblaga.jetlagtool.domain.geojson.GeoJsonFeatureRepository

interface Factory<T> {
    fun create(): T
}

class MapViewModelFactory(
    private val geoJsonFeatureRepository: GeoJsonFeatureRepository
) : Factory<MapViewModel> {

    override fun create(): MapViewModel {
        return MapViewModel(geoJsonFeatureRepository)
    }

}