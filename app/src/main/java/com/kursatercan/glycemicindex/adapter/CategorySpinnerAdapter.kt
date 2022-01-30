package com.kursatercan.glycemicindex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.kursatercan.glycemicindex.databinding.ItemSpinnerCategoryBinding
import com.kursatercan.glycemicindex.model.Category
import java.util.*
import kotlin.collections.ArrayList

class CategorySpinnerAdapter (private val categoryList:ArrayList<Category>, val context: Context) :
    BaseAdapter() {
    override fun getCount(): Int {
        return categoryList.size
    }

    override fun getItem(p0: Int): Any {
        return categoryList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val bind = ItemSpinnerCategoryBinding.inflate(LayoutInflater.from(context),p2,false)
        val item = categoryList[p0]
        bind.tvCategoryTitle.text = item.title

        return bind.root
    }
}