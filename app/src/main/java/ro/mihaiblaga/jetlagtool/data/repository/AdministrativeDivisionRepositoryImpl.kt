package ro.mihaiblaga.jetlagtool.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import ro.mihaiblaga.jetlagtool.data.local.dao.AdministrativeDivisionDao
import ro.mihaiblaga.jetlagtool.data.local.entity.AdministrativeDivisionEntity
import ro.mihaiblaga.jetlagtool.domain.model.AdministrativeDivision
import ro.mihaiblaga.jetlagtool.domain.repository.AdministrativeDivisionRepository
import ro.mihaiblaga.jetlagtool.domain.repository.FeatureRepository

class AdministrativeDivisionRepositoryImpl(
    private val dao: AdministrativeDivisionDao,
    private val featureRepository: FeatureRepository
) : AdministrativeDivisionRepository {

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override suspend fun getAdministrativeDivisions(): List<AdministrativeDivision> {
        val administrativeDivisions = dao.getAllAdministrativeDivisions()
        return administrativeDivisions.map { mapEntityToDomain(it) }
    }

    override suspend fun getAdministrativeDivisionById(id: Long): AdministrativeDivision? {
        return dao.getAdministrativeDivisionById(id)?.let { mapEntityToDomain(it) }
    }

    private suspend fun mapEntityToDomain(entity: AdministrativeDivisionEntity): AdministrativeDivision {
        return AdministrativeDivision(
            id = entity.id,
            level = entity.level,
            type = entity.type,
            name = entity.name,
            feature = featureRepository.getFeatureById(entity.featureId)
                ?: throw Exception("Feature not found")
        )
    }
}