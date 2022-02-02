package com.kursatercan.glycemicindex.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kursatercan.glycemicindex.R
import com.kursatercan.glycemicindex.RealmDBActionListener
import com.kursatercan.glycemicindex.RealmDBActionListenerReferences
import com.kursatercan.glycemicindex.databinding.ItemFoodBinding
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.CurrentFood
import com.kursatercan.glycemicindex.model.Food
import com.kursatercan.glycemicindex.view.ModifyFoodActivity


class FoodAdapter(val context: Context, private var foodList:ArrayList<Food>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>(),
    RealmDBActionListener {
    class ViewHolder(val bind : ItemFoodBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        RealmDBActionListenerReferences.foodAdapterListener=this
        val bind = ItemFoodBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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
                val intent = Intent(context, ModifyFoodActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra("position",position)
                context.startActivity(intent)

            }
            tvAddFavourite.setOnClickListener {
                // TODO : Favori fragmentinde recycler view otomatik güncellenmeli
                RealmDBActionListenerReferences.favouritesFragmentListener?.onFavouriesChanged()
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

    override fun onAddedFood(food: Food) {
        val cid = foodList[0].cid

        foodList = DBManager(context).getFoods(cid)
        notifyDataSetChanged()
        //super.onAddedFood(food)
    }

    override fun onModifiedFood(food: Food, position: Int) {
        super.onModifiedFood(food, position)
        foodList[position] = food
        notifyItemChanged(position)
    }

    override fun onRemovedFood(position: Int) {
        //super.onRemovedFood(food)
        //notifyItemRemoved(position)
        foodList.removeAt(position)
        notifyDataSetChanged()
    }
}