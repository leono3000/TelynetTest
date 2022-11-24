package com.argel.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.argel.myapplication.adapter.ClientsAdapter
import com.argel.myapplication.databinding.ActivityMainBinding
import com.argel.myapplication.views.ClientsViewModel
import com.argel.myapplication.views.DispatcherFilter
import com.argel.myapplication.views.FiltersDialog

class MainActivity : AppCompatActivity(), DispatcherFilter {

    private val viewModel: ClientsViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    lateinit var clientAdapter: ClientsAdapter
    private var filters = IntArray(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.initData()
        initUI()
        setSupportActionBar(binding.toolbar)
    }

    private fun initUI() {
        clientAdapter = ClientsAdapter(this, this)
        binding.clientsRv.layoutManager = LinearLayoutManager(this)
        binding.clientsRv.adapter = clientAdapter
        filters[0] = 2
        filters[1] = 2
        clientAdapter.addClients(viewModel.getClientsFromRepo())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filters -> {
                FiltersDialog(this, this, filters).createDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun sendFiltersSelected(filtersSelected: IntArray) {
        filters = filtersSelected
        clientAdapter.filterVisible(filtersSelected[0], filtersSelected[1])
    }
}
