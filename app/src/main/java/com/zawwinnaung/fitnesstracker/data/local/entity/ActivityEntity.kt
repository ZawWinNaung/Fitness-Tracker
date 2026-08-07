package com.zawwinnaung.fitnesstracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val img: Int,
)