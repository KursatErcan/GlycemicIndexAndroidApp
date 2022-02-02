package com.kursatercan.glycemicindex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.kursatercan.glycemicindex.R
import com.kursatercan.glycemicindex.databinding.ActivityMainBinding
import com.kursatercan.glycemicindex.view.fragment.CategoriesFragment
import com.kursatercan.glycemicindex.view.fragment.FavouritesFragment
import com.kursatercan.glycemicindex.view.fragment.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bind:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.gradient_main))

        //Repostory().getDataFromSource(this)


        val categoriesFragment = CategoriesFragment()
        val searchFragment = SearchFragment()
        val favouritesFragment = FavouritesFragment()

        changeFragment(categoriesFragment)

        bind.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.categoriesFragment -> changeFragment(categoriesFragment)
                R.id.searchFragment -> changeFragment(searchFragment)
                R.id.favouritesFragment -> changeFragment(favouritesFragment)
            }
            true
        }

    }

    private fun changeFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(bind.frameLayout.id,fragment)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.refreshDB -> startActivity(Intent(this@MainActivity,RefreshDbActivity::class.java))
            R.id.newItem -> startActivity(Intent(this@MainActivity,EditActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}