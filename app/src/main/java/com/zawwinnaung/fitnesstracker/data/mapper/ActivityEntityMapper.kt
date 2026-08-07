package com.zawwinnaung.fitnesstracker.data.mapper

import com.zawwinnaung.fitnesstracker.data.local.entity.ActivityEntity
import com.zawwinnaung.fitnesstracker.domain.model.Activity

fun List<ActivityEntity>.toDomain(): List<Activity> {
    return this.map {
        Activity(
            id = it.id,
            title = it.title,
            img = it.img
        )
    }
}

fun List<Activity>.toEntity(): List<ActivityEntity> {
    return this.map {
        ActivityEntity(
            id = it.id,
            title = it.title,
            img = it.img
        )
    }
}