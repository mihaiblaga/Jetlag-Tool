package ro.mihaiblaga.jetlagtool.presentation

import ro.mihaiblaga.jetlagtool.domain.repository.FeatureRepository

interface Factory<T> {
    fun create(): T
}

class MapViewModelFactory(
    private val featureRepository: FeatureRepository
) : Factory<MapViewModel> {

    override fun create(): MapViewModel {
        return MapViewModel(featureRepository)
    }

}