package com.kursatercan.glycemicindex.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.kursatercan.glycemicindex.R
import com.kursatercan.glycemicindex.RealmDBActionListener
import com.kursatercan.glycemicindex.RealmDBActionListenerReferences
import com.kursatercan.glycemicindex.databinding.ItemFoodBinding
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.CurrentFood
import com.kursatercan.glycemicindex.model.Food
import com.kursatercan.glycemicindex.util.ListenerRef
import com.kursatercan.glycemicindex.view.MainActivity
import com.kursatercan.glycemicindex.view.fragment.ModifyFoodDialogFragment


class FoodAdapter(val context: Context, private var foodList:ArrayList<Food>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>(),
    RealmDBActionListener,ModifyFoodDialogActions {
    private lateinit var mContext: Context
    private lateinit var cid : String

    class ViewHolder(val bind : ItemFoodBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        RealmDBActionListenerReferences.foodAdapterListener=this
        val bind = ItemFoodBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        mContext = parent.context
        return ViewHolder(bind)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodList[position]
        holder.bind.apply {
            tvFoodName.text = "Besin Adı:\n" + food.name

            val glIndex = food.glysemicIndex
            val color: Int = when {
                glIndex>=71 -> {
                    Color.RED
                }
                glIndex in 55..69 -> {
                    Color.YELLOW
                }
                else -> {
                    Color.GREEN
                }
            }

            tvGlycemicIndex.setBackgroundColor(color)
            tvGlycemicIndex.text = "gl. İndeks:\n"+food.glysemicIndex.toString()
            tvCarbohydrateAmount.text = "karb. mik.:\n"+food.carbohydrateAmount
            tvCalorie.text = "kalori:\n"+ food.calorie

            if(!food.favouriteState) {
                ivFavouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite))
            }
            else {
                ivFavouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_filled))
            }

            ivFoodExpand.setOnClickListener {
                foodExpandLayout.visibility = if(foodExpandLayout.visibility == View.GONE){
                    ivFoodExpand.setImageDrawable(context.getDrawable(R.drawable.ic_expand_less))
                    View.VISIBLE
                } else{
                    ivFoodExpand.setImageDrawable(context.getDrawable(R.drawable.ic_expand_more))
                    View.GONE
                }
            }
            tvModifyFood.setOnClickListener {
                CurrentFood.food = food
                cid=food.cid
                ListenerRef.modifyFoodDialogListener = this@FoodAdapter
                val newFragment: DialogFragment = ModifyFoodDialogFragment().newInstance(food.fid,position)
                newFragment.show((mContext as MainActivity).supportFragmentManager, "dialogModifyFood")

            }
            tvAddFavourite.setOnClickListener {
                RealmDBActionListenerReferences.favouritesFragmentListener?.onFavouriesChanged()
                if(food.favouriteState){
                    Log.d("ilk favori durumu", ""+food.favouriteState)
                    DBManager(context).getRealm()?.executeTransaction { food.favouriteState = false }
                    Log.d("ikinci favori durumu", ""+food.favouriteState)

                    ivFavouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite))
                }
                else{
                    Log.d("ilk favori durumu", ""+food.favouriteState)
                    DBManager(context).getRealm()?.executeTransaction { food.favouriteState = true }
                    Log.d("ikinci favori durumu", ""+food.favouriteState)

                    ivFavouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_filled))
                }
            }
        }
    }
    // TODO : Bunlar silinebilir !!
    override fun getItemCount(): Int = foodList.size

    override fun onAddedFood(food: Food) {
        val cid = foodList[0].cid

        foodList = DBManager(context).getFoods(cid)
        notifyDataSetChanged()
    }

    override fun onModifiedFood(food: Food, position: Int) {
        super.onModifiedFood(food, position)
        foodList[position] = food
        notifyItemChanged(position)
    }

    override fun onRemovedFood(position: Int) {
        foodList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onUpdateFood(food: Food,position: Int) {
        foodList[position] = food
        notifyItemChanged(position)
    }

    override fun onRemoveFood(position: Int) {
        foodList.removeAt(position)
        notifyItemRemoved(position)
    }

}

interface ModifyFoodDialogActions{
    fun onUpdateFood(food: Food,position: Int)
    fun onRemoveFood(position: Int)
}