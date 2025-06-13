package ro.mihaiblaga.jetlagtool.data.converters

import androidx.room.TypeConverter
import ro.mihaiblaga.jetlagtool.domain.model.Category

class CategoryConverter {
    @TypeConverter
    fun fromCategory(category: Category): String {
        return category.name
    }

    @TypeConverter
    fun toCategory(value: String): Category {
        return Category.valueOf(value)
    }

}