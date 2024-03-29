package com.mario.desafiojuno.data.local.converter

import androidx.room.TypeConverter
import java.util.*


class ConverterDate {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return (date?.time)
    }
}



