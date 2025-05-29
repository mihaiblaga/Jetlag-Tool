package ro.mihaiblaga.jetlagtool.domain.repository

import ro.mihaiblaga.jetlagtool.domain.model.AdministrativeDivision

interface AdministrativeDivisionRepository {
    suspend fun getAdministrativeDivisions(): List<AdministrativeDivision>

    suspend fun getAdministrativeDivisionById(id: Long): AdministrativeDivision?
}