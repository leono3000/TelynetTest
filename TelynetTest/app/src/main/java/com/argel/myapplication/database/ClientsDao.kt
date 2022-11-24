package com.argel.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ClientsDao {

    @Insert
    fun insertClient(clientsEntity: ClientsEntity)

    @Query("SELECT * FROM clients")
    fun getClients(): List<ClientsEntity>
}

