package com.mario.desafiojuno.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mario.desafiojuno.data.local.entity.Result
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface ResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResult(result: Result): Long

    @Update
    fun updateResult(result: Result)

    @Query("SELECT * FROM result WHERE id = :id")
    fun getResultById(id: String): LiveData<Result>

    @Delete
    fun deleteResult(result: Result)

    @Query("SELECT * FROM result")
    fun loadResults(): Single<List<Result>>

    @Query("DELETE FROM result")
    fun removeAll()
}