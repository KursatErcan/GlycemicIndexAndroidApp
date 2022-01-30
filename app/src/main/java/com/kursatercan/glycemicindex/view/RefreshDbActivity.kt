package com.kursatercan.glycemicindex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import com.kursatercan.glycemicindex.R
import com.kursatercan.glycemicindex.Repostory
import com.kursatercan.glycemicindex.databinding.ActivityRefreshDbBinding
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.Category
import com.kursatercan.glycemicindex.model.Food
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import kotlin.math.log

class RefreshDbActivity : AppCompatActivity() {
    private lateinit var bind : ActivityRefreshDbBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRefreshDbBinding.inflate(layoutInflater)
        setContentView(bind.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        bind.btnCancel.setOnClickListener {
            onBackPressed()
        }

        bind.btnUpdate.setOnClickListener {
            //bind.btnUpdate.isEnabled = false
            //bind.btnCancel.isEnabled = false
            bind.btnUpdate.setBackgroundColor(getColor(R.color.button_update_disable))
            bind.tvInfoText.setText(getString(R.string.on_data_fetching_info))

            Repostory().updateDataFromSource(this)

            //bind.btnUpdate.isEnabled = true
            //bind.btnCancel.isEnabled = true
            bind.btnUpdate.setBackgroundColor(getColor(R.color.button_update_enable))
            bind.tvInfoText.setText(getString(R.string.update_info))

        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == 16908332){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}