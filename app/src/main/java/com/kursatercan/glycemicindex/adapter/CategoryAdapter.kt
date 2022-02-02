package com.kursatercan.glycemicindex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kursatercan.glycemicindex.R
import com.kursatercan.glycemicindex.databinding.ItemCategoryBinding
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.Category
import com.kursatercan.glycemicindex.model.CurrentCategory
import com.kursatercan.glycemicindex.util.ListenerRef
import com.kursatercan.glycemicindex.view.MainActivity
import com.kursatercan.glycemicindex.view.fragment.dialog.ModifyCategoryDialogFragment

class CategoryAdapter(val context: Context, private val categoryList:ArrayList<Category>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>()
    ,ModifyCategoryDialogActions {
    private lateinit var mContext: Context
    class ViewHolder(val bind : ItemCategoryBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        mContext = parent.context
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categoryList[position]
        holder.bind.apply {
            tvCategoryName.text = category.title
            val adapter = FoodAdapter(context, DBManager(context).getFoods(category.cid))
            rvMeals.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            rvMeals.adapter = adapter

            this.root.setOnClickListener {
                rvMeals.visibility = if(rvMeals.visibility == View.GONE) {
                    View.VISIBLE
                } else{
                    categoryExpandLayout.visibility = View.GONE
                    View.GONE
                }
                ivCategoryExpand.visibility = if(ivCategoryExpand.visibility == View.GONE) View.VISIBLE else View.GONE
            }

            ivCategoryExpand.setOnClickListener {
                categoryExpandLayout.visibility = if(categoryExpandLayout.visibility == View.GONE) {
                    ivCategoryExpand.setImageDrawable(context.getDrawable(R.drawable.ic_expand_less))
                    View.VISIBLE
                } else {
                    ivCategoryExpand.setImageDrawable(context.getDrawable(R.drawable.ic_expand_more))
                    View.GONE
                }
            }

            modifyCategory.setOnClickListener {
                CurrentCategory.category = category

                ListenerRef.modifyCategoryDialogListener = this@CategoryAdapter

                val newFragment: DialogFragment = ModifyCategoryDialogFragment().newInstance(category.cid,position)
                newFragment.show((mContext as MainActivity).supportFragmentManager, "dialogModifyCategory")

            }
        }
    }

    override fun getItemCount(): Int = categoryList.size
    override fun onUpdateCategory(category: Category, position: Int) {
        categoryList[position] = category
        notifyItemChanged(position)
    }

    override fun onRemoveCategory(position: Int) {
        categoryList.removeAt(position)
        notifyItemChanged(position)
    }

}

interface ModifyCategoryDialogActions{
    fun onUpdateCategory(category: Category, position: Int)
    fun onRemoveCategory(position: Int)
}