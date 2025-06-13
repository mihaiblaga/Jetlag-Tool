package ro.mihaiblaga.jetlagtool.data.repository

import ro.mihaiblaga.jetlagtool.domain.model.AdministrativeDivision
import ro.mihaiblaga.jetlagtool.domain.repository.AdministrativeDivisionRepository

class FakeAdministrativeDivisionRepositoryImpl : AdministrativeDivisionRepository {
    override suspend fun getAdministrativeDivisions(): List<AdministrativeDivision> {
        TODO("Not yet implemented")
    }

    override suspend fun getAdministrativeDivisionById(id: Long): AdministrativeDivision? {
        TODO("Not yet implemented")
    }
}