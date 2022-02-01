package com.kursatercan.glycemicindex

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.Category
import com.kursatercan.glycemicindex.model.Food
import com.kursatercan.glycemicindex.view.MainActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class Repostory {
    //private lateinit var db: DBManager
    private val url: String =
        "http://kolaydoktor.com/saglik-icin-yasam/diyet-ve-beslenme/besinlerin-glisemik-indeks-tablosu/0503/1"

    fun getDataFromSource(context: Context) {
        //db = DBManager(context)

        val thread = Thread {
            try {
                val doc: Document = Jsoup.connect(url).get()
                //println("Categories : ${d.outerHtml()}")
                val tables: Elements = doc.select("table")
                //val rows :Elements = tables.get(0).select("tbody").select("tr")

                tables.forEach {
                    val rows: Elements = it.select("tbody").select("tr")
                    var cid = ""
                    for ((index, row) in rows.withIndex()) {
                        if (index == 0) {
                            val title = row.select("p").text()
                            // TODO : buradan emin değilim patlarsa burda patlar -- nereye döneceğini belirtmek için bi key vardı ona bak!
                            if (title.trim().isEmpty()) continue

                            Log.d("Category : ", title)

                            val category = Category()
                            category.title = title
                            DBManager(context).addCategory(category)
                            Log.d("burdasın : ", "getDataFromSource: ")
                            cid = category.cid
                        } else if (index == 1) {
                            Log.d(
                                "Colum Names : (${row.select("p").size}) -- ",
                                row.select("p").text()
                            )
                        } else {
                            val elements: Elements = row.select("p")
                            val food = Food()
                            food.cid = cid
                            food.name = elements[0].text()
                            food.glysemicIndex = elements[1].text().trim().toInt()
                            food.carbohydrateAmount = elements[2].text().replace(',','.')
                            food.calorie = elements[3].text().trim()

                            DBManager(context).addFood(food)

                            //Log.d("Item : (${row.select("p").size})  -- ", row.select("p").text())
                            Log.d("Item : (${row.select("p").size})  -- ", food.name+" "+food.glysemicIndex + " " + food.carbohydrateAmount + " "+ food.calorie)

                        }

                    }
                }
            } catch (ex: Exception) {
                Log.d("HTML PARSER : ", ex.toString())

            }
        }
        thread.start()
    }

    fun updateDataFromSource(context: Context) {
        Log.d("updateDataFromSource", "updateDataFromSource: burdasın")
        //db = DBManager(context)
        val thread = Thread {
            try {
                val doc: Document = Jsoup.connect(url).get()
                val tables: Elements = doc.select("table")
                //val rows :Elements = tables.get(0).select("tbody").select("tr")

                tables.forEach {
                    val rows: Elements = it.select("tbody").select("tr")
                    var cid = ""
                    for ((index, row) in rows.withIndex()) {
                        if (index == 0) {
                            val title = row.select("p").text()

                            if (title.trim().isEmpty() ||  DBManager(context).getCategory(title) == 1) {
                                Log.d("Added New Category : ", title)
                                continue
                            }else{
                                val category = Category()
                                category.title = title
                                DBManager(context).addCategory(category)
                                cid = category.cid
                            }

                        } else if (index == 1) {
                            Log.d(
                                "Colum Names : (${row.select("p").size}) -- ",
                                row.select("p").text()
                            )
                        } else {
                            val elements: Elements = row.select("p")
                            val food = Food()

                            food.cid = cid
                            food.name = elements[0].text()
                            food.glysemicIndex = elements[1].text().trim().toInt()
                            food.carbohydrateAmount = elements[2].text().replace(',','.')
                            food.calorie = elements[3].text().trim()

                            if(DBManager(context).getFood(food.name,food.glysemicIndex,food.carbohydrateAmount,food.calorie)==1){
                                continue
                            }else{
                                DBManager(context).addFood(food)
                            }


                            Log.d("Item : (${row.select("p").size})  -- ", row.select("p").text())

                        }

                    }
                }
            } catch (ex: Exception) {
                Log.d("HTML PARSER : ", ex.toString())

            }
        }
        thread.start()

    }
}