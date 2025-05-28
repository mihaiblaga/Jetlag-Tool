package ro.mihaiblaga.jetlagtool.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ro.mihaiblaga.jetlagtool.data.local.DatabaseSchema
import ro.mihaiblaga.jetlagtool.data.model.AdministrativeDivision

@Dao
interface AdministrativeDivisionDao {

    @Insert
    fun insert(administrativeDivision: AdministrativeDivision): Long

    @Query("SELECT * FROM ${DatabaseSchema.TABLE_ADMINISTRATIVE_DIVISION} WHERE id = :id")
    fun getAdministrativeDivisionById(id: Long): AdministrativeDivision? // TODO: Flow or LiveData?
}