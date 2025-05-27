package ro.mihaiblaga.jetlagtool.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ro.mihaiblaga.jetlagtool.data.local.dao.AdministrativeDivisionDao
import ro.mihaiblaga.jetlagtool.data.local.dao.BorderGeometryDao
import ro.mihaiblaga.jetlagtool.data.model.AdministrativeDivision
import ro.mihaiblaga.jetlagtool.data.model.BorderGeometry

@Database(
    entities = [
        AdministrativeDivision::class,
        BorderGeometry::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun administrativeDivisionDao(): AdministrativeDivisionDao
    abstract fun borderGeometryDao(): BorderGeometryDao

}