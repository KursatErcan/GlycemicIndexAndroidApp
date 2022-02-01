package com.kursatercan.glycemicindex.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kursatercan.glycemicindex.R
import com.kursatercan.glycemicindex.databinding.ItemFoodBinding
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.CurrentFood
import com.kursatercan.glycemicindex.model.Food
import com.kursatercan.glycemicindex.view.ModifyFoodActivity


class FoodAdapter(val context: Context, val foodList:ArrayList<Food>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>()   {
    class ViewHolder(val bind : ItemFoodBinding) : RecyclerView.ViewHolder(bind.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = ItemFoodBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(bind)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodList[position]
        holder.bind.apply {
            tvFoodName.text = food.name
            tvGlycemicIndex.text = food.glysemicIndex.toString()
            tvCarbohydrateAmount.text = food.carbohydrateAmount
            tvCalorie.text = food.calorie

            if(!food.favouriteState) {
                ivFavouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite))
            }
            else {
                ivFavouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_filled))
            }

            // TODO : foodExpandLayout içerisindeki elemanlara click listener ekle

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
                val intent = Intent(context, ModifyFoodActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)

            }
            tvAddFavourite.setOnClickListener {
                // TODO : Favori fragmentinde recycler view otomatik güncellenmeli
                if(food.favouriteState){
                    Log.d("ilk favori durumu", ""+food.favouriteState)
                    DBManager(context).getRealm()?.executeTransaction { food.favouriteState = false }
                    Log.d("ikinci favori durumu", ""+food.favouriteState)

                    ivFavouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite))
                    //notifyDataSetChanged()
                    //onBindViewHolder(holder, position)
                }
                else{
                    Log.d("ilk favori durumu", ""+food.favouriteState)
                    DBManager(context).getRealm()?.executeTransaction { food.favouriteState = true }
                    Log.d("ikinci favori durumu", ""+food.favouriteState)

                    ivFavouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_filled))
                    //notifyDataSetChanged()
                    //onBindViewHolder(holder, position)
                }
            }
        }
    }

    override fun getItemCount(): Int = foodList.size

}