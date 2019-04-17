package com.mario.desafiojuno.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Result(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @SerializedName("items")
    val items: List<Item>,

    @SerializedName("total_count")
    val totalCount: Int
)