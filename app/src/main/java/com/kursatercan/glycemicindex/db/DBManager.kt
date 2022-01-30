package com.kursatercan.glycemicindex.db

import android.content.Context
import com.kursatercan.glycemicindex.model.Category
import com.kursatercan.glycemicindex.model.Food
import io.realm.Realm
import io.realm.kotlin.where

class DBManager (context:Context) {
    private val db:DB = DB(context)

    fun addCategory(category: Category){
        db.config()?.executeTransaction { it.insert(category) }
    }
    fun addFood(food: Food){
        db.config()?.executeTransaction { it.insert(food) }
    }

    fun getCategories():ArrayList<Category>{
        val list = ArrayList<Category>()
        db.config()?.executeTransaction {
            it.where<Category>().findAll().forEach {
                list.add(it)
            }
        }
        return list
    }
    fun getFoods():ArrayList<Food>{ // bütün food'ları getirir
        val list = ArrayList<Food>()
        db.config()?.executeTransaction {
            it.where<Food>().findAll().forEach {
                list.add(it)
            }
        }
        return list
    }
    fun getFoods(cid:String): ArrayList<Food> { // cid kategori numarasına sahip olan food'ları getirir
        val list = ArrayList<Food>()
        db.config()?.executeTransaction {
            it.where<Food>().equalTo("cid",cid).findAll().forEach {
                list.add(it)
            }
        }
        return list
        //return db.config()?.where<Food>()!!.equalTo("cid",cid)

    }
    fun getFood(name:String,glysemicIndex: Int?,carbohydrateAmount: String?,calorie: String?): Int { //
        return db.config()?.where<Food>()!!
            .equalTo("name",name)
            .equalTo("glysemicIndex",glysemicIndex)
            .equalTo("carbohydrateAmount",carbohydrateAmount)
            .equalTo("calorie",calorie)
            .findAll().size
    }
    fun getCategory(title:String): Int { //
        return db.config()?.where<Category>()!!
            .equalTo("title",title)
            .findAll().size
    }
    fun removeCategory(cid: String){
        val category = db.config()?.where(Category::class.java)
            ?.equalTo("cid", cid)
            ?.findFirst()

        db.config()?.executeTransaction {
            category!!.deleteFromRealm()
        }
    }
    fun removeFoodWithCID(cid: String){
        val foods = db.config()?.where(Food::class.java)
            ?.equalTo("cid", cid)?.findAll()

        db.config()?.executeTransaction {
            foods!!.forEach{
                it.deleteFromRealm()
            }
        }
    }
    fun removeFoodWithFID(fid: String){
        val foods = db.config()?.where(Food::class.java)
            ?.equalTo("fid", fid)?.findAll()

        db.config()?.executeTransaction {
            foods!!.forEach{
                it.deleteFromRealm()
            }
        }
    }

    /*

    fun getNote(uid:String): Note? {
        return db.config()?.where<Note>()!!.equalTo("uid",uid).findFirst()

    }

     */
    /*
    //title: String,noteText: String
    fun updateNote(note: Note){
        db.config()?.executeTransaction {
            /*val note = Note()
            note.uid = noteData.uid
            note.title = noteData.title
            note.noteText = noteData.noteText
            */
            it.insertOrUpdate(note)
        }
    }
    fun removeNote(uid: String){
        val note = db.config()?.where(Note::class.java)
            ?.equalTo("uid", uid)
            ?.findFirst()

        db.config()?.executeTransaction {
            note!!.deleteFromRealm()
        }
    }

    fun removeAllNotes() {
        db.config()?.executeTransaction(Realm.Transaction {
                realm ->
            realm.deleteAll();
            realm.delete(Note::class.java) })

    }
    */
}