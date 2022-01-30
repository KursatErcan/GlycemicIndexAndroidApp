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

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentCategoriesBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        /*bind?.cardView?.setOnClickListener {
           //TransitionManager.beginDelayedTransition(bind?.layout, AutoTransition())
            bind?.rv_meals?.visibility = if(bind?.rv_meals?.visibility == View.GONE) View.VISIBLE else View.GONE
        }*/
        categoryList = DBManager(requireActivity().baseContext).getCategories()
        adapter = CategoryAdapter(requireActivity().baseContext,categoryList)

        bind?.rvCategories?.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL,false)
        bind?.rvCategories?.adapter = adapter

        return binding.root
    }



}