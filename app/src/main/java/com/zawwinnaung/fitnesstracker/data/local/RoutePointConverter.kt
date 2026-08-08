package com.zawwinnaung.fitnesstracker.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zawwinnaung.fitnesstracker.domain.model.RoutePoint

class RoutePointConverter {
    private val gson = Gson()

    @TypeConverter
    fun routePointListToJson(value: List<RoutePoint>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toRoutePointList(value: String?): List<RoutePoint> {
        if (value.isNullOrEmpty()) return emptyList()
        val type = object : TypeToken<List<RoutePoint>>() {}.type
        return gson.fromJson(value, type)
    }
}