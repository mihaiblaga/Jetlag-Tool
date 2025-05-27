package ro.mihaiblaga.jetlagtool.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ro.mihaiblaga.jetlagtool.data.model.AdministrativeDivision

@Dao
interface AdministrativeDivisionDao {

    @Insert
    suspend fun insert(administrativeDivision: AdministrativeDivision): Long

    @Query("SELECT * FROM administrative_divisions WHERE id = :id")
    suspend fun getAdministrativeDivisionById(id: Long): AdministrativeDivision?
}