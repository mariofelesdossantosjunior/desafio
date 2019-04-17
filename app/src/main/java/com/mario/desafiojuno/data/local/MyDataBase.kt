package com.mario.desafiojuno.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mario.desafiojuno.data.local.converter.ConverterDate
import com.mario.desafiojuno.data.local.converter.ConverterList
import com.mario.desafiojuno.data.local.dao.ResultDao
import com.mario.desafiojuno.data.local.entity.Result

@Database(
    entities = [
        (Result::class)
    ], version = 1, exportSchema = false
)

@TypeConverters(ConverterDate::class, ConverterList::class)

abstract class MyDataBase : RoomDatabase() {

    abstract fun resultDao(): ResultDao
}