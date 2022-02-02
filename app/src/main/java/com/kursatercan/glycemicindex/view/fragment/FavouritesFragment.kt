package com.kursatercan.glycemicindex.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kursatercan.glycemicindex.RealmDBActionListener
import com.kursatercan.glycemicindex.RealmDBActionListenerReferences
import com.kursatercan.glycemicindex.adapter.FoodAdapter
import com.kursatercan.glycemicindex.databinding.FragmentFavouritesBinding
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.Food


class FavouritesFragment : Fragment(), RealmDBActionListener {
    private var bind : FragmentFavouritesBinding? = null
    private val binding get() = bind!!

    private lateinit var favouriteFoodsList :ArrayList<Food>
    private lateinit var adapter: FoodAdapter
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity().baseContext
        RealmDBActionListenerReferences.favouritesFragmentListener=this

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentFavouritesBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        favouriteFoodsList = DBManager(mContext).getFavourites()

        adapter = FoodAdapter(mContext, favouriteFoodsList)
        bind!!.rvFoods.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        bind!!.rvFoods.adapter = adapter

        if (favouriteFoodsList.size > 0){
            bind!!.rvFoods.visibility = View.VISIBLE
            bind!!.tvFavouriteInfo.visibility = View.GONE
        }else{
            bind!!.rvFoods.visibility = View.GONE
            bind!!.tvFavouriteInfo.visibility = View.VISIBLE
        }


        return binding.root
    }

    override fun onFavouriesChanged() {
        favouriteFoodsList = DBManager(mContext).getFavourites()
        adapter.notifyDataSetChanged()

        //super.onFavouriesChanged()
        //Log.d("ON ADDED CATEGORY", "FAVOURİTES FRAGMENT ")
    }

}