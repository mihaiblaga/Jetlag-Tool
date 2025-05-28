package ro.mihaiblaga.jetlagtool.core.di

import android.content.Context
import org.maplibre.android.MapLibre
import org.maplibre.android.WellKnownTileServer
import ro.mihaiblaga.jetlagtool.BuildConfig
import ro.mihaiblaga.jetlagtool.data.local.AppDatabase
import ro.mihaiblaga.jetlagtool.data.repository.FileFeatureRepositoryImpl
import ro.mihaiblaga.jetlagtool.domain.repository.FeatureRepository
import ro.mihaiblaga.jetlagtool.presentation.MapViewModel
import ro.mihaiblaga.jetlagtool.presentation.MapViewModelFactory

interface AppModule {
    val database: AppDatabase
    val featureRepository: FeatureRepository
    val map: MapLibre
    val mapViewModel: MapViewModel
}

class AppModuleImpl(
    appContext: Context
) : AppModule {
    val mapLibreApiKey = BuildConfig.MAPLIBRE_ACCESS_TOKEN

    override val map: MapLibre = MapLibre.getInstance(
        appContext,
        mapLibreApiKey,
        WellKnownTileServer.MapTiler,
    )
    override val database: AppDatabase by lazy {
        AppDatabase.getInstance(appContext)
    }

    override val featureRepository: FeatureRepository =
        FileFeatureRepositoryImpl(appContext)

    override val mapViewModel: MapViewModel = MapViewModelFactory(featureRepository).create()

}