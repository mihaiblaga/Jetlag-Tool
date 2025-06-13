package ro.mihaiblaga.jetlagtool.data.converters

import androidx.room.TypeConverter
import ro.mihaiblaga.jetlagtool.domain.model.GameSize

class GameSizeConverter {
    private val separator = ","

    @TypeConverter
    fun fromGameSizes(gameSizes: List<GameSize>): String {
        return gameSizes.joinToString(separator) { it.name }
    }

    @TypeConverter
    fun toGameSizes(value: String): List<GameSize> {
        return value.split(separator).map { GameSize.valueOf(it) }
    }
}