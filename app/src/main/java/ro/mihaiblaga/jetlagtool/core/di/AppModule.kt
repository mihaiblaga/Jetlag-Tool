package ro.mihaiblaga.jetlagtool.core.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ro.mihaiblaga.jetlagtool.data.local.AppDatabase
import ro.mihaiblaga.jetlagtool.data.repository.AdministrativeDivisionRepositoryImpl
import ro.mihaiblaga.jetlagtool.data.repository.FeatureRepositoryImpl
import ro.mihaiblaga.jetlagtool.domain.repository.AdministrativeDivisionRepository
import ro.mihaiblaga.jetlagtool.domain.repository.FeatureRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return AppDatabase.getInstance(app.applicationContext)
    }

    @Provides
    @Singleton
    fun provideAdministrativeDivisionRepository(database: AppDatabase): AdministrativeDivisionRepository {
        return AdministrativeDivisionRepositoryImpl(
            database.administrativeDivisionDao(),
            provideFeatureRepository(database)
        )
    }

    @Provides
    @Singleton
    fun provideFeatureRepository(database: AppDatabase): FeatureRepository {
        return FeatureRepositoryImpl(
            database.featureDao()
        )
    }
}