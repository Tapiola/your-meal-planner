package com.example.yourmealplanner.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    val email: String,
    val password: String,
    @PrimaryKey(autoGenerate = true) var id: Long = 0
)