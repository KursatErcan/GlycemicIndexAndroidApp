package com.kursatercan.glycemicindex.db

import android.content.Context
import com.kursatercan.glycemicindex.model.Category
import com.kursatercan.glycemicindex.model.Food
import io.realm.kotlin.where

class DBManager (context:Context) {
    private val realm = DB(context).config()

    fun getRealm() = realm

    fun addCategory(category: Category){
        realm?.executeTransaction { it.insert(category) }
    }
    fun addFood(food: Food){
        realm?.executeTransaction { it.insert(food) }
    }

    fun getCategories():ArrayList<Category>{
        val list = ArrayList<Category>()
        realm?.executeTransaction {
            it.where<Category>().findAll().forEach {
                list.add(it)
            }
        }
        return list
    }
    fun getFoods():ArrayList<Food>{ // bütün food'ları getirir
        val list = ArrayList<Food>()
        realm?.executeTransaction {
            it.where<Food>().findAll().forEach {
                list.add(it)
            }
        }
        return list
    }
    fun getFoods(cid:String): ArrayList<Food> { // cid kategori numarasına sahip olan food'ları getirir
        val list = ArrayList<Food>()
        realm?.executeTransaction {
            it.where<Food>().equalTo("cid",cid).findAll().forEach {
                list.add(it)
            }
        }
        return list
        //return db.config()?.where<Food>()!!.equalTo("cid",cid)
    }
    fun getFavourites() : ArrayList<Food>{
        val list = ArrayList<Food>()
        realm?.executeTransaction {
            it.where<Food>()
                .equalTo("favouriteState",true)
                .findAll()
                .forEach {
                    list.add(it)
                }
        }
        return list
    }
    fun getFood(fid: String): Food { //
        return realm?.where<Food>()!!
            .equalTo("fid",fid)
            .findFirst() as Food
    }
    //category check
    fun getFood(name:String,glysemicIndex: Int?,carbohydrateAmount: String?,calorie: String?): Int { //
        return realm?.where<Food>()!!
            .equalTo("name",name)
            .equalTo("glysemicIndex",glysemicIndex)
            .equalTo("carbohydrateAmount",carbohydrateAmount)
            .equalTo("calorie",calorie)
            .findAll().size
    }
    fun getCategoryObj(title:String): Category { //
        return realm?.where<Category>()!!
            .equalTo("title",title)
            .findFirst() as Category
    }
    fun getCategoryObjWithCID(cid:String): Category { //
        return realm?.where<Category>()!!
            .equalTo("cid",cid)
            .findFirst() as Category
    }
    // category check
    fun getCategory(title:String): Int { //
        return realm?.where<Category>()!!
            .equalTo("title",title)
            .findAll().size
    }

    fun removeCategory(cid: String){
        removeFoodWithCID(cid)
        val category = realm?.where(Category::class.java)
            ?.equalTo("cid", cid)
            ?.findFirst()

        realm?.executeTransaction {
            category!!.deleteFromRealm()
        }
    }
    private fun removeFoodWithCID(cid: String){
        val foods = realm?.where(Food::class.java)
            ?.equalTo("cid", cid)?.findAll()

        realm?.executeTransaction {
            foods!!.forEach{
                it.deleteFromRealm()
            }
        }
    }
    fun removeFoodWithFID(fid: String){
        val foods = realm?.where(Food::class.java)
            ?.equalTo("fid", fid)?.findFirst()

        realm?.executeTransaction {
            foods!!.deleteFromRealm()
        }
    }
}