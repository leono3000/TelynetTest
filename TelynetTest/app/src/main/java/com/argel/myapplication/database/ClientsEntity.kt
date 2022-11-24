package com.argel.myapplication.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clients")
data class ClientsEntity(
    @PrimaryKey(autoGenerate = true)
    var userId: Int? = null,
    val userName: String,
    val userEmail: String,
    val userPhone: String,
    val userCode: String,
    val userStatus: String
)