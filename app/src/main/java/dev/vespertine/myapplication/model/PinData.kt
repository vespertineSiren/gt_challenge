package dev.vespertine.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Entity(tableName = "pin_data")
@Serializable
data class PinData(
    val description: String? = "",
    @PrimaryKey
    @ColumnInfo(name = "pin_id")
    val id: Int,
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
    val name: String? = null
)
