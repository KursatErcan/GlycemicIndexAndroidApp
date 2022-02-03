package com.kursatercan.glycemicindex

import android.content.Context
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.Category
import com.kursatercan.glycemicindex.model.Food
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class Repostory {
    private val url: String =
        "http://kolaydoktor.com/saglik-icin-yasam/diyet-ve-beslenme/besinlerin-glisemik-indeks-tablosu/0503/1"

    fun getDataFromSource(context: Context) {

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
                        if (index == 0) { // category title
                            val title = row.select("p").text()
                            if (title.trim().isEmpty()) continue

                            //Log.d("Category : ", title)

                            val category = Category()
                            category.title = title
                            DBManager(context).addCategory(category)
                            cid = category.cid
                        } else if (index == 1) {
                            //column titles

                            /*Log.d(
                                "Colum Names : (${row.select("p").size}) -- ",
                                row.select("p").text()
                            )*/
                        } else { // foods in category
                            val elements: Elements = row.select("p")
                            val food = Food()
                            food.cid = cid
                            food.name = elements[0].text()
                            food.glysemicIndex = elements[1].text().trim().toInt()
                            food.carbohydrateAmount = elements[2].text().replace(',','.')
                            food.calorie = elements[3].text().trim()

                            DBManager(context).addFood(food)

                            //Log.d("Item : (${row.select("p").size})  -- ", row.select("p").text())
                            //Log.d("Item : (${row.select("p").size})  -- ", food.name+" "+food.glysemicIndex + " " + food.carbohydrateAmount + " "+ food.calorie)

                        }
                    }
                }
            } catch (ex: Exception) {
                //Log.d("HTML PARSER : ", ex.toString())
            }
        }
        thread.start()
    }

    fun updateDataFromSource(context: Context) {

        val thread = Thread {
            try {
                val doc: Document = Jsoup.connect(url).get()
                val tables: Elements = doc.select("table")
                //val rows :Elements = tables.get(0).select("tbody").select("tr")

                tables.forEach {
                    val rows: Elements = it.select("tbody").select("tr")
                    var cid = ""
                    for ((index, row) in rows.withIndex()) {
                        if (index == 0) { // category
                            val title = row.select("p").text()

                            if (title.trim().isEmpty() ||  DBManager(context).getCategory(title) == 1) {
                                cid = DBManager(context).getCategoryObj(title).cid
                                //Log.d("Added New Category : ", title)
                                continue
                            }else{
                                val category = Category()
                                category.title = title
                                DBManager(context).addCategory(category)
                                cid = category.cid
                            }

                        } else if (index == 1) { // column titles
                            /*Log.d(
                                "Colum Names : (${row.select("p").size}) -- ",
                                row.select("p").text()
                            )*/
                        } else { // foods in category
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
                            //Log.d("Item : (${row.select("p").size})  -- ", row.select("p").text())
                        }
                    }
                }
            } catch (ex: Exception) {
                //Log.d("HTML PARSER : ", ex.toString())
            }
        }
        thread.start()

    }
}