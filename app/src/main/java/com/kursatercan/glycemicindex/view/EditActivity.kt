package com.kursatercan.glycemicindex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.kursatercan.glycemicindex.R
import com.kursatercan.glycemicindex.adapter.CategorySpinnerAdapter
import com.kursatercan.glycemicindex.databinding.ActivityEditBinding
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.Category
import com.kursatercan.glycemicindex.model.Food

class EditActivity : AppCompatActivity() {
    private lateinit var bind : ActivityEditBinding
    private lateinit var categoryList: ArrayList<Category>
    private lateinit var foodList: ArrayList<Food>
    private lateinit var db : DBManager
    private lateinit var categorySpinnerAdapter: CategorySpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityEditBinding.inflate(layoutInflater)
        setContentView(bind.root)
        db = DBManager(this)
        categoryList = db.getCategories()
        foodList = db.getFoods()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        categorySpinnerAdapter = CategorySpinnerAdapter(categoryList,this)
        bind.spinnerCategory.adapter =categorySpinnerAdapter

    }

    fun onCategoryButtonClicked(view: View){
        if(bind.btnCategoryButton.text == resources.getText(R.string.save)){
            val title = bind.etCategoryTitle.text.toString().trim()
            if(title != ""){
                val c = Category()
                c.title = title
                db.addCategory(c)
                bind.etCategoryTitle.setText("")

            }
        }else{
            val selectedCategory = bind.spinnerCategory.selectedItem as Category

            db.removeFoodWithCID(selectedCategory.cid)
            db.removeCategory(selectedCategory.cid)
            categoryList = db.getCategories()
            foodList = db.getFoods()

            bind.spinnerCategory.setSelection(0,true)
            categoryList.remove(selectedCategory)
            categorySpinnerAdapter.notifyDataSetChanged()
        }

    }

    fun onCategoryEditExpandClicked(view: View){
        if(bind.categoryEditLayout.visibility == View.GONE){
            bind.categoryEditLayout.visibility = View.VISIBLE
            bind.ivCategoryEditExpand.setImageDrawable(getDrawable(R.drawable.ic_expand_less))
        }else{
            bind.categoryEditLayout.visibility = View.GONE
            bind.ivCategoryEditExpand.setImageDrawable(getDrawable(R.drawable.ic_expand_more))
        }
}

    fun onCategoryEditSwitchClicked(view: View){
        if(bind.switchCategory.isChecked){
            bind.etCategoryTitle.visibility = View.VISIBLE
            bind.spinnerCategory.visibility = View.GONE
            bind.btnCategoryButton.text = resources.getText(R.string.save)
        }else{
            bind.etCategoryTitle.visibility = View.GONE
            bind.spinnerCategory.visibility = View.VISIBLE
            bind.btnCategoryButton.text = resources.getText(R.string.delete)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == 16908332){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


}