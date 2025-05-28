package ro.mihaiblaga.jetlagtool.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ro.mihaiblaga.jetlagtool.data.local.DatabaseSchema
import ro.mihaiblaga.jetlagtool.data.model.BorderGeometry

@Dao
interface BorderGeometryDao {
    @Insert
    fun insert(borderGeometry: BorderGeometry): Long

    @Query("SELECT * FROM ${DatabaseSchema.TABLE_BORDER_GEOMETRY} WHERE id = :id")
    fun getBorderGeometryById(id: Long): BorderGeometry?

}