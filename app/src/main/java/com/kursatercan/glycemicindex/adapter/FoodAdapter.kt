package com.kursatercan.glycemicindex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kursatercan.glycemicindex.R
import com.kursatercan.glycemicindex.databinding.ItemFoodBinding
import com.kursatercan.glycemicindex.model.Food

class FoodAdapter(val context: Context, val foodList:ArrayList<Food>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>()   {
    class ViewHolder(val bind : ItemFoodBinding) : RecyclerView.ViewHolder(bind.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodAdapter.ViewHolder {
        val bind = ItemFoodBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: FoodAdapter.ViewHolder, position: Int) {
        val food = foodList[position]
        holder.bind.apply {
            tvFoodName.text = food.name
            tvGlycemicIndex.text = food.glysemicIndex.toString()
            tvCarbohydrateAmount.text = food.carbohydrateAmount
            tvCalorie.text = food.calorie
            // TODO

            /*if(food.favouriteState){
                ivFavouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite))
            }
            else{
                ivFavouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_filled))
            }
            */
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
                // TODO : Dialog aç
            }
            tvAddFavourite.setOnClickListener {
                // TODO
                /*if(food.favouriteState){
                    food.favouriteState = false
                    ivFavouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite))
                }
                else{
                    food.favouriteState = true
                    ivFavouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_filled))
                }

                 */
            }
        }
    }

    override fun getItemCount(): Int = foodList.size
}