package com.kursatercan.glycemicindex

import com.kursatercan.glycemicindex.model.Category
import com.kursatercan.glycemicindex.model.Food

interface RealmDBActionListener {
    fun onAddedFood(food: Food){}
    fun onAddedCategory(category: Category){}
    fun onRemovedCategory(position: Int){}
    fun onRemovedFood(position: Int){}
    fun onModifiedFood(food: Food,position: Int){}
    fun onModifiedCategory(category:Category){}
    fun onFavouriesChanged(){}
}

object RealmDBActionListenerReferences{
    var categoryFragmentListener : RealmDBActionListener? = null
    var favouritesFragmentListener : RealmDBActionListener? = null
    var foodAdapterListener : RealmDBActionListener? = null

}