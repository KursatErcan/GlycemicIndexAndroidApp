package com.kursatercan.glycemicindex.view.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.kursatercan.glycemicindex.RealmDBActionListener
import com.kursatercan.glycemicindex.RealmDBActionListenerReferences
import com.kursatercan.glycemicindex.adapter.CategoryAdapter
import com.kursatercan.glycemicindex.databinding.FragmentCategoriesBinding
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.Category

class CategoriesFragment : Fragment(), RealmDBActionListener {
    private var bind : FragmentCategoriesBinding? = null
    private val binding get() = bind!!
    private lateinit var container: ViewGroup
    private lateinit var adapter: CategoryAdapter
    private lateinit var categoryList :ArrayList<Category>
    private lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        mContext = requireActivity().baseContext
        RealmDBActionListenerReferences.categoryFragmentListener=this
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

    override fun onAddedCategory(category: Category) {
        Log.d("ON ADDED CATEGORY", "CATEGORİES FRAGMENT ")

        categoryList.add(category)
        adapter.notifyItemInserted(categoryList.size-1)
        /*
        val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false)
        }
        ft.detach(this).attach(this).commit()
        */

    }

    override fun onRemovedCategory(position: Int) {
        categoryList = DBManager(mContext).getCategories()
        adapter.notifyDataSetChanged()
    }

    override fun onRemovedFood(position: Int) {
        val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false)
        }
        ft.replace(this.container.id,this).commit()
        //ft.detach(this).attach(this).commit()
    }

    override fun onModifiedCategory(category: Category) {
        //super.onModifiedCategory(category)

        categoryList = DBManager(mContext).getCategories()
        adapter.notifyDataSetChanged()
    }


}

