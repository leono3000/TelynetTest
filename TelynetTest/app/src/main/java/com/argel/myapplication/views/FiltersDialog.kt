package com.argel.myapplication.views

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import com.argel.myapplication.R

open class FiltersDialog(private val mContext: Context, private val filterCb: DispatcherFilter, private val getFilters: IntArray) : Dialog(mContext) {

    var filtersSelected = IntArray(2)

    init {
        dialog = Dialog(mContext)
    }

    fun createDialog() {
        dialog?.let {
            it.requestWindowFeature(Window.FEATURE_NO_TITLE)
            it.setContentView(R.layout.filters_dialog)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setCancelable(false)

            filtersSelected[0] = getFilters[0]
            filtersSelected[1] = getFilters[1]
//            if (getFilters[0] != 1 || getFilters[1] != -1) {
//                filtersSelected[0] = getFilters[0]
//                filtersSelected[1] = getFilters[1]
//            } else {
//                filtersSelected[0] = -1
//                filtersSelected[1] = -1
//            }

            val acceptButton = it.findViewById(R.id.btnAccept) as Button
            val filtersGroup = it.findViewById(R.id.filtersContainer) as RadioGroup
            val orderByGroup = it.findViewById(R.id.orderByContainer) as RadioGroup

            filtersGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rBtnVisited -> filtersSelected[0] = 0
                    R.id.rBtnNotVisited -> filtersSelected[0] = 1
                    R.id.rBtnAll -> filtersSelected[0] = 2
                }
            }

            orderByGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rBtnCode -> filtersSelected[1] = 0
                    R.id.rBtnName -> filtersSelected[1] = 1
                    R.id.rBtnAllOfThem -> filtersSelected[1] = 2
                }
            }

            val filtersUIArray = setCheckedFilters(filtersSelected)

            val filtersButton = filtersGroup.findViewById<RadioButton>(filtersUIArray[0])
            filtersButton.isChecked = true

            val orderByButton = orderByGroup.findViewById<RadioButton>(filtersUIArray[1])
            orderByButton.isChecked = true

            acceptButton.setOnClickListener { _ ->
                filterCb.sendFiltersSelected(filtersSelected)
                it.dismiss()
            }

            it.show()
        }
    }

    private fun setCheckedFilters(filters: IntArray): IntArray {
        val selection = IntArray(2)

        if (filters[0] == 0) {
            selection[0] = R.id.rBtnVisited
        } else if (filters[0] == 1) {
            selection[0] = R.id.rBtnNotVisited
        } else if (filters[0] == 2) {
            selection[0] = R.id.rBtnAll
        }

        if (filters[1] == 0) {
            selection[1] = R.id.rBtnCode
        } else if (filters[1] == 1) {
            selection[1] = R.id.rBtnName
        } else if (filters[1] == 2) {
            selection[1] = R.id.rBtnAllOfThem
        }
        return selection
    }

    companion object {
        var dialog: Dialog? = null
    }
}

interface DispatcherFilter {
    fun sendFiltersSelected(filtersSelected: IntArray)
}
