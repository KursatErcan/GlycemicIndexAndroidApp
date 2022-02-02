package com.kursatercan.glycemicindex.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kursatercan.glycemicindex.R
import com.kursatercan.glycemicindex.Repostory
import com.kursatercan.glycemicindex.databinding.ActivityRefreshDbBinding

class RefreshDbActivity : AppCompatActivity() {
    private lateinit var bind : ActivityRefreshDbBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRefreshDbBinding.inflate(layoutInflater)
        setContentView(bind.root)
        supportActionBar?.title = "Veri Tabanını Güncelle"
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.gradient_main))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        bind.btnCancel.setOnClickListener {
            onBackPressed()
        }

        bind.btnUpdate.setOnClickListener {

            bind.tvInfoText.text = getString(R.string.on_data_fetching_info)

            Repostory().updateDataFromSource(this)

            bind.tvInfoText.text = getString(R.string.update_info)
            Toast.makeText(this, "Veritabanı güncellendi!", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == 16908332){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}