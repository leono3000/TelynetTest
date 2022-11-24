package com.argel.myapplication.repository

import android.content.Context
import android.os.AsyncTask
import com.argel.myapplication.database.ClientsDao
import com.argel.myapplication.database.ClientsEntity
import com.argel.myapplication.database.TelynetDatabase

class ClientsRepository(ctx: Context) {

    var db: ClientsDao = TelynetDatabase.getInstance(ctx)?.clientsDao()!!

    fun getAllClients(): List<ClientsEntity> = db.getClients()

    fun insertClient(clientsEntity: ClientsEntity) = insertAsyncTask(db).execute(clientsEntity)

    private class insertAsyncTask internal constructor(private val clientsDao: ClientsDao) : AsyncTask<ClientsEntity, Void, Void>() {
        override fun doInBackground(vararg p0: ClientsEntity?): Void? {
            clientsDao.insertClient(p0[0]!!)
            return null
        }
    }
}
