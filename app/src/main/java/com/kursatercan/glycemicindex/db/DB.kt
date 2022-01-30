package com.kursatercan.glycemicindex.db

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class DB (private val context: Context) {

    fun config() : Realm?{
        Realm.init(context)
        val conf = RealmConfiguration.Builder()
            .name("temp1.db")
            .schemaVersion(1)
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .build()
        return Realm.getInstance(conf)
    }

}
