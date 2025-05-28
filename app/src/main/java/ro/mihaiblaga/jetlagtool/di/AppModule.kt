package ro.mihaiblaga.jetlagtool.di

import android.content.Context
import org.maplibre.android.MapLibre
import org.maplibre.android.WellKnownTileServer
import ro.mihaiblaga.jetlagtool.BuildConfig
import ro.mihaiblaga.jetlagtool.data.local.AppDatabase
import ro.mihaiblaga.jetlagtool.data.repository.FileGeoJsonFeatureRepositoryImpl
import ro.mihaiblaga.jetlagtool.domain.geojson.GeoJsonFeatureRepository
import ro.mihaiblaga.jetlagtool.presentation.MapViewModel
import ro.mihaiblaga.jetlagtool.presentation.MapViewModelFactory

interface AppModule {
    val database: AppDatabase
    val geoJsonFeatureRepository: GeoJsonFeatureRepository
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
    override val database: AppDatabase = TODO("Not yet implemented")

    override val geoJsonFeatureRepository: GeoJsonFeatureRepository =
        FileGeoJsonFeatureRepositoryImpl(appContext)

    override val mapViewModel: MapViewModel = MapViewModelFactory(geoJsonFeatureRepository).create()

}