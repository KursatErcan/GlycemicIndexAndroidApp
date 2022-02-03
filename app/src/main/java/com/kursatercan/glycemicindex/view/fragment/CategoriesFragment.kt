package com.kursatercan.glycemicindex.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kursatercan.glycemicindex.adapter.CategoryAdapter
import com.kursatercan.glycemicindex.databinding.FragmentCategoriesBinding
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.Category
import com.kursatercan.glycemicindex.util.ListenerRef

class CategoriesFragment : Fragment(), CategoriesFragmentListener {
    private var bind : FragmentCategoriesBinding? = null
    private val binding get() = bind!!
    private lateinit var container: ViewGroup
    private lateinit var adapter: CategoryAdapter
    private lateinit var categoryList :ArrayList<Category>
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        mContext = requireActivity().baseContext
        ListenerRef.categoriesFragmentRef = this
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentCategoriesBinding.inflate(inflater,container,false)
        this.container = container!!
        categoryList = DBManager(mContext).getCategories()
        adapter = CategoryAdapter(mContext,categoryList)

        bind?.rvCategories?.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL,false)
        bind?.rvCategories?.adapter = adapter

        return binding.root
    }

    override fun onNewFoodAdded() {
        adapter.notifyDataSetChanged()
    }

    override fun onNewCategoryAdded(category : Category) {
        categoryList.add(category)
        adapter.notifyItemInserted(categoryList.size-1)
    }

    override fun onDbRefresh() {
        categoryList = DBManager(mContext).getCategories()
        adapter.notifyDataSetChanged()
    }

}

interface CategoriesFragmentListener{
    fun onNewFoodAdded()
    fun onNewCategoryAdded(category : Category)
    fun onDbRefresh()
}

