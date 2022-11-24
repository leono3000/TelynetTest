package com.argel.myapplication.adapter

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.argel.myapplication.BR
import com.argel.myapplication.R
import com.argel.myapplication.database.ClientsEntity

class ClientsAdapter(private val ctx: Context, private val activity: Activity) : RecyclerView.Adapter<ClientsAdapter.ClientsViewHolder>() {

    private val items = ArrayList<ClientsEntity>()
    private lateinit var originalList: List<ClientsEntity>

    fun addClients(clients: List<ClientsEntity>) {
        this.items.clear()
        this.items.addAll(clients)
        this.originalList = clients
        notifyDataSetChanged()
    }

    private fun removeItemsForFilter(filter: String, order: Int) {
        val tempList = arrayListOf<ClientsEntity>()
        tempList.addAll(originalList)
        originalList.forEach { if (it.userStatus == filter) tempList.remove(it) }
        items.clear()
        items.addAll(sortedList(order, tempList))
    }

    private fun sortedList(order: Int, tempList: ArrayList<ClientsEntity>): ArrayList<ClientsEntity> {
        var sortedList = arrayListOf<ClientsEntity>()
        when (order) {
            0 -> { sortedList.addAll(tempList.sortedWith(compareBy { it.userCode })) }
            1 -> { sortedList.addAll(tempList.sortedWith(compareBy { it.userName })) }
            2 -> { sortedList.addAll(tempList.sortedWith(compareBy { it.userId })) }
        }
        return sortedList
    }

    private fun resetData(order: Int) {
        val tempList = arrayListOf<ClientsEntity>()
        tempList.addAll(originalList)
        items.clear()
        items.addAll(sortedList(order, tempList))
    }

    fun filterVisible(filter: Int, order: Int) {
        when (filter) {
            0 -> removeItemsForFilter(NOT_VISITED, order)
            1 -> removeItemsForFilter(VISITED, order)
            2 -> resetData(order)
        }
        notifyDataSetChanged()
    }

    private fun sortListByName() {
        val tempList = ArrayList<ClientsEntity>()
        tempList.addAll(originalList)
        tempList.sortedWith(compareBy { it.userName })
        items.clear()
        items.addAll(tempList)
    }

    private fun sortListByCode() {
        val tempList = ArrayList<ClientsEntity>()
        tempList.addAll(originalList)
        tempList.sortedWith(compareBy { it.userCode })
        items.clear()
        items.addAll(tempList)
    }

    private fun resetSortedData() {
        items.sortedWith(compareBy { it.userId })
    }

    fun orderBy(value: Int) {
        when (value) {
            0 -> sortListByCode()
            1 -> sortListByName()
            2 -> resetSortedData()
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutId = R.layout.client_card
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layoutId, parent, false)
        return ClientsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClientsViewHolder, position: Int) {
        val itemViewModel = items[holder.adapterPosition]
        holder.itemView.findViewById<ImageView>(R.id.imageView).setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:${items[position].userPhone}")
            if (intent.resolveActivity(ctx.packageManager) != null) {
                if (ActivityCompat.checkSelfPermission(
                        ctx,
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestForCallPermission()
                } else {
                    ctx.startActivity(intent)
                }
            }
        }
        holder.bind(itemViewModel)
    }

    private fun requestForCallPermission(): Boolean {
        return if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.CALL_PHONE
            )
        ) {
            true
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.CALL_PHONE),
                1
            )
            false
        }
    }

    override fun getItemCount() = items.size

    class ClientsViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemViewModel: ClientsEntity) {
            binding.setVariable(
                BR.client, itemViewModel
            )
            binding.executePendingBindings()
        }
    }

    companion object {
        const val NOT_VISITED = "No Visitado"
        const val VISITED = "Visitado"
        const val ORDER_BY_CODE = "Code"
        const val ORDER_BY_NAME = "Name"
    }
}
