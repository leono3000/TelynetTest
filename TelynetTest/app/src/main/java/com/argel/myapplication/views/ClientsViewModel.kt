package com.argel.myapplication.views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.argel.myapplication.database.ClientsEntity
import com.argel.myapplication.repository.ClientsRepository

class ClientsViewModel(application: Application) : AndroidViewModel(application) {

    private val clientRepo: ClientsRepository by lazy {
        ClientsRepository(application.applicationContext)
    }

    fun initData() {
        if (clientRepo.getAllClients().isEmpty()) {
            insertTestClients()
        }
    }

    fun getClientsFromRepo(): List<ClientsEntity> {
        return clientRepo.getAllClients()
    }

    private fun insertTestClients() {
        clientRepo.insertClient(ClientsEntity(1, "Argel Tellez", "argel@mail.com", "5645678670", "000000", "Visitado"))
        clientRepo.insertClient(ClientsEntity(2, "Aruturo Hernandez", "arturo@mail.com", "5645678671", "111111", "No Visitado"))
        clientRepo.insertClient(ClientsEntity(3, "Robert Garcia", "robert@mail.com", "5645678672", "222222", "Visitado"))
        clientRepo.insertClient(ClientsEntity(4, "Ramon Flores", "ramon@mail.com", "5645678673", "333333", "No Visitado"))
        clientRepo.insertClient(ClientsEntity(5, "Hernesto Zedillo", "hernesto@mail.com", "5645678674", "444444", "Visitado"))
        clientRepo.insertClient(ClientsEntity(6, "Ricardo Salinas", "ricardo@mail.com", "5645678675", "555555", "No Visitado"))
        clientRepo.insertClient(ClientsEntity(7, "Andres Lopez", "andres@mail.com", "5645678676", "666666", "Visitado"))
        clientRepo.insertClient(ClientsEntity(8, "Manuel Vega", "manuel@mail.com", "5645678677", "777777", "No Visitado"))
        clientRepo.insertClient(ClientsEntity(9, "Angel Campos", "angel@mail.com", "5645678678", "888888", "Visitado"))
        clientRepo.insertClient(ClientsEntity(10, "Daniel Angeles", "daniel@mail.com", "5645678679", "999999", "No Visitado"))
    }
}
