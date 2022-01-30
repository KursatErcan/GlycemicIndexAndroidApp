package com.kursatercan.glycemicindex.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Category :RealmObject(){
    @PrimaryKey
    var cid:String = UUID.randomUUID().toString()
    var title:String = ""
}