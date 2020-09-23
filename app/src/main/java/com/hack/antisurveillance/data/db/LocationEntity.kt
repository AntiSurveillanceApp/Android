package com.hack.antisurveillance.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val latitude: Double,
    val longitude: Double
) {
    override fun toString(): String {
        return "lat: $latitude, lon:$longitude, id: $id"
    }
}