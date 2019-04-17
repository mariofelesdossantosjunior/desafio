package com.mario.desafiojuno.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mario.desafiojuno.data.local.entity.Item

class ConverterList {

    @TypeConverter
    fun listToJson(value: List<Item>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Item>? {
        val objects = Gson().fromJson(value, Array<Item>::class.java) as Array<Item>
        return objects.toList()
    }
}