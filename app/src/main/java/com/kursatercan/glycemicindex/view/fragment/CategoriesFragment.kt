package com.kursatercan.glycemicindex.view.fragment

import android.content.Context
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kursatercan.glycemicindex.adapter.CategoryAdapter
import com.kursatercan.glycemicindex.databinding.FragmentCategoriesBinding
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.Category
import com.kursatercan.glycemicindex.model.Food

class CategoriesFragment : Fragment() {
    private var bind : FragmentCategoriesBinding? = null
    private val binding get() = bind!!

    private lateinit var adapter: CategoryAdapter
    private lateinit var categoryList :ArrayList<Category>
    private lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        mContext = requireActivity().baseContext
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentCategoriesBinding.inflate(inflater,container,false)

        categoryList = DBManager(mContext).getCategories()
        adapter = CategoryAdapter(mContext,categoryList)

        bind?.rvCategories?.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL,false)
        bind?.rvCategories?.adapter = adapter

        return binding.root
    }

}
