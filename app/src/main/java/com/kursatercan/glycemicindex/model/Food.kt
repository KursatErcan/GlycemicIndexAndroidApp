package com.kursatercan.glycemicindex.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Food : RealmObject(){
    @PrimaryKey
    var fid:String = UUID.randomUUID().toString()
    var cid: String =""
    var name: String =""
    var glysemicIndex: Int = 0  //  0-54 : 55-69 : 70-
    var carbohydrateAmount: String = ""
    var calorie: String = ""
    var favouriteState: Boolean = false

}

object CurrentFood {
    var food : Food? = null
}