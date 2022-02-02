package com.kursatercan.glycemicindex.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kursatercan.glycemicindex.Repostory
import com.kursatercan.glycemicindex.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    private val appUsingData = "APP_USING_DATA"
    private val firstOpen = "FIRST_OPEN"
    private lateinit var bind:ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bind.root)
        supportActionBar?.hide()

        val pref = this.getSharedPreferences(appUsingData, Context.MODE_PRIVATE)
        val isFirstOpen = pref.getBoolean(firstOpen,false)

        if (!isFirstOpen) {
            bind.tvNote.visibility = View.VISIBLE
            Repostory().getDataFromSource(this)
            pref.edit().putBoolean(firstOpen, true).apply()
        }

        Handler().postDelayed(Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }, 3000)

        /*
        Timer().schedule(object : TimerTask() {
            override fun run() {
                val intent = Intent(this@SplashActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 1500)

         */
    }
}