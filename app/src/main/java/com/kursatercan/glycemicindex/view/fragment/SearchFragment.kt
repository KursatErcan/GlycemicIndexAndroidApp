package com.kursatercan.glycemicindex.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kursatercan.glycemicindex.adapter.FoodAdapter
import com.kursatercan.glycemicindex.databinding.FragmentSearchBinding
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.Category
import com.kursatercan.glycemicindex.model.Food


class SearchFragment : Fragment() {
    private var bind : FragmentSearchBinding? = null
    private val binding get() = bind!!
    private lateinit var adapter: FoodAdapter
    private lateinit var foodList :ArrayList<Food>
    private var tempFoodList = ArrayList<Food>()
    private lateinit var categoryList :ArrayList<Category>
    private lateinit var mContext: Context
    private lateinit var db : DBManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity().baseContext
        db= DBManager(mContext)
        foodList = db.getFoods()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentSearchBinding.inflate(inflater, container, false)
        tempFoodList.addAll(foodList)
        categoryList = db.getCategories()

        adapter = FoodAdapter(mContext, tempFoodList)
        bind?.apply {
            rvFoods.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvFoods.adapter = adapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    searchView.clearFocus()
                    if(p0?.trim()?.isEmpty() == true){
                        tempFoodList.clear()
                        tempFoodList.addAll(foodList)
                        return false
                    }
                    else{
                        if(p0 != null){
                            findSearchResults(p0)
                            adapter.notifyDataSetChanged()
                            return true
                        }
                        else return false
                    }
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    if(p0?.trim()?.isEmpty() == true){
                        tempFoodList.clear()
                        tempFoodList.addAll(foodList)
                        return false
                    }
                    else{
                        if(p0 != null){
                            findSearchResults(p0)
                            adapter.notifyDataSetChanged()
                            return true
                        }
                        else return false
                    }
                }
            })
        }

        return binding.root
    }
    /**
     * Saves the search results. Returns the number of results found back
    */
    private fun findSearchResults(query:String) : Int{
        val temp:MutableSet<Food> = HashSet()

        foodList.forEach {
            if(it.name.lowercase().contains(query.lowercase())){
                temp.add(it)
            }
        }
        categoryList.forEach {
            if(it.title.lowercase().contains(query.lowercase())){
                temp.addAll(db.getFoods(it.cid))
            }
        }
        tempFoodList.clear()
        tempFoodList.addAll(temp)

        return tempFoodList.size
    }
}