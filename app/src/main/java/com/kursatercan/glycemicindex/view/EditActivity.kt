package com.kursatercan.glycemicindex.view

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.kursatercan.glycemicindex.R
import com.kursatercan.glycemicindex.adapter.CategorySpinnerAdapter
import com.kursatercan.glycemicindex.databinding.ActivityEditBinding
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.Category
import com.kursatercan.glycemicindex.model.Food

class EditActivity : AppCompatActivity() {
    private lateinit var bind : ActivityEditBinding
    private lateinit var categoryList: ArrayList<Category>
    private lateinit var db : DBManager
    private lateinit var categorySpinnerAdapter: CategorySpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityEditBinding.inflate(layoutInflater)
        setContentView(bind.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        db = DBManager(this)
        categoryList = db.getCategories()


        categorySpinnerAdapter = CategorySpinnerAdapter(categoryList,this)
        bind.spinnerCategory.adapter = categorySpinnerAdapter

    }
    private fun createCategory(){
        // TODO recyclerı güncelle

        val category = Category()
        category.title = bind.etCategoryTitle.text.toString()
        db.addCategory(category)

        bind.etCategoryTitle.setText("")
        categoryList.add(category)
        categorySpinnerAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Yeni kategori oluşturuldu.", Toast.LENGTH_SHORT).show()

    }

    private fun createFood(){
        // TODO recyclerı güncelle
        val selectedCategory = bind.spinnerCategory.selectedItem as Category
        val food = Food()
        food.cid = selectedCategory.cid
        food.name = bind.etFoodName.text.toString()
        food.glysemicIndex = bind.etGlysemicIndex.text.toString().toInt()
        food.carbohydrateAmount = bind.etCarbohydrateAmount.text.toString()
        food.calorie = bind.etCalorie.text.toString()

        db.addFood(food)
        bind.spinnerCategory.setSelection(0)
        bind.etFoodName.setText("")
        bind.etGlysemicIndex.setText("")
        bind.etCarbohydrateAmount.setText("")
        bind.etCalorie.setText("")
        Toast.makeText(this, "Yeni besin eklendi.", Toast.LENGTH_SHORT).show()

    }


    fun onCategorySaveButtonClicked(view: View){

        val title = bind.etCategoryTitle.text.toString().trim()
        if(title.isEmpty()) bind.etCategoryTitle.error = getString(R.string.empty_tv_error_message)
        else{
           saveCategoryDialog().show()
        }


    }

    fun onFoodSaveButtonClicked(view: View){
        bind.apply {
            if (etFoodName.text.isEmpty()) etFoodName.error = getString(R.string.empty_tv_error_message)
            if (etGlysemicIndex.text.isEmpty()) etGlysemicIndex.error = getString(R.string.empty_tv_error_message)
            if (etCarbohydrateAmount.text.isEmpty()) etCarbohydrateAmount.error = getString(R.string.empty_tv_error_message)
            if (etCalorie.text.isEmpty()) etCalorie.error = getString(R.string.empty_tv_error_message)

            if(etFoodName.text.isNotEmpty()
                && etGlysemicIndex.text.isNotEmpty()
                && etCarbohydrateAmount.text.isNotEmpty()
                && etCalorie.text.isNotEmpty()
            ){
                saveFoodDialog().show()
            }
        }
    }

    private fun saveCategoryDialog() : AlertDialog {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Yeni Kategori Ekle")
        alertDialogBuilder.setMessage("Yeni kategori oluşturmak istiyor musunuz?")
        alertDialogBuilder.setPositiveButton("İptal") { dialogInterface: DialogInterface, i: Int ->
        }
        alertDialogBuilder.setNegativeButton("Oluştur") { dialogInterface: DialogInterface, i: Int ->
            createCategory()
        }

        return alertDialogBuilder.create()
    }

    private fun saveFoodDialog() : AlertDialog {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Yeni Besin Ekle")
        alertDialogBuilder.setMessage("Yeni besin oluşturmak istiyor musunuz?")
        alertDialogBuilder.setPositiveButton("İptal") { dialogInterface: DialogInterface, i: Int ->
        }
        alertDialogBuilder.setNegativeButton("Oluştur") { dialogInterface: DialogInterface, i: Int ->
            createFood()
        }

        return alertDialogBuilder.create()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == 16908332){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


}