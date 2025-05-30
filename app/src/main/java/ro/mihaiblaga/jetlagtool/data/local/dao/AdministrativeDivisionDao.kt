package ro.mihaiblaga.jetlagtool.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ro.mihaiblaga.jetlagtool.data.local.DatabaseSchema
import ro.mihaiblaga.jetlagtool.data.local.entity.AdministrativeDivisionEntity

@Dao
interface AdministrativeDivisionDao {

    @Insert
    suspend fun insert(administrativeDivisionEntity: AdministrativeDivisionEntity): Long

    @Query("SELECT * FROM ${DatabaseSchema.TABLE_ADMINISTRATIVE_DIVISION} WHERE id = :id")
    suspend fun getAdministrativeDivisionById(id: Long): AdministrativeDivisionEntity? // TODO: Flow or LiveData?

    @Query("SELECT * FROM ${DatabaseSchema.TABLE_ADMINISTRATIVE_DIVISION}")
    suspend fun getAllAdministrativeDivisions(): List<AdministrativeDivisionEntity>

}