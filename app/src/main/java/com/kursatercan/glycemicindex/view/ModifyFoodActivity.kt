package com.kursatercan.glycemicindex.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.kursatercan.glycemicindex.adapter.CategorySpinnerAdapter
import com.kursatercan.glycemicindex.databinding.ActivityModifyFoodBinding
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.Category
import com.kursatercan.glycemicindex.model.CurrentFood
import com.kursatercan.glycemicindex.model.Food

class ModifyFoodActivity : AppCompatActivity() {
    private lateinit var bind : ActivityModifyFoodBinding
    private lateinit var categoryList: ArrayList<Category>
    private lateinit var db : DBManager
    private lateinit var categorySpinnerAdapter: CategorySpinnerAdapter
    private val food : Food = CurrentFood.food!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityModifyFoodBinding.inflate(layoutInflater)
        setContentView(bind.root)

        db = DBManager(this)
        categoryList = db.getCategories()
        categorySpinnerAdapter = CategorySpinnerAdapter(categoryList,this)
        bind.spinnerCategory.adapter =categorySpinnerAdapter
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)


        bind.apply {
            spinnerCategory.setSelection( categoryFind() )
            etFoodName.setText(food.name)
            etGlysemicIndex.setText(food.glysemicIndex.toString())
            etCarbohydrateAmount.setText(food.carbohydrateAmount)
            etCalorie.setText(food.calorie)

            btnSave.setOnClickListener {
                if (etFoodName.text.isEmpty()) etFoodName.error = "Bu alan boş bırakılamaz!"
                if (etGlysemicIndex.text.isEmpty()) etGlysemicIndex.error = "Bu alan boş bırakılamaz!"
                if (etCarbohydrateAmount.text.isEmpty()) etCarbohydrateAmount.error = "Bu alan boş bırakılamaz!"
                if (etCalorie.text.isEmpty()) etCalorie.error = "Bu alan boş bırakılamaz!"

                if(etFoodName.text.isNotEmpty()
                    && etGlysemicIndex.text.isNotEmpty()
                    && etCarbohydrateAmount.text.isNotEmpty()
                    && etCalorie.text.isNotEmpty()
                ){
                    saveDialog().show()
                }
            }

            btnRemove.setOnClickListener {
                removeDialog(food.name).show()
            }
        }




    }
    private fun updateFood(){
        // TODO recyclerı güncelle
        val selectedCategory = bind.spinnerCategory.selectedItem as Category
        db.getRealm()?.executeTransaction {
            food.cid = selectedCategory.cid
            food.name = bind.etFoodName.text.toString()
            food.glysemicIndex = bind.etGlysemicIndex.text.toString().toInt()
            food.carbohydrateAmount = bind.etCarbohydrateAmount.text.toString()
            food.calorie = bind.etCalorie.text.toString()
        }
        Toast.makeText(this, "Değişiklikler kaydedildi.", Toast.LENGTH_SHORT).show()



    }

    private fun removeFood(){
        db.removeFoodWithFID(food.fid)
        Toast.makeText(this, "Ürün başarıyla silindi.", Toast.LENGTH_SHORT).show()
        finish()
    }
    private fun categoryFind() : Int{
        for ((index, category) in categoryList.withIndex()) {
            if(category.cid == food.cid) return index
        }
        return 0
    }

    private fun saveDialog() : AlertDialog{
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Besin Bilgilerini Güncelle")
        alertDialogBuilder.setMessage("Besin bilgilerini değiştirmek istiyor musunuz?")
        alertDialogBuilder.setPositiveButton("İptal") { dialogInterface: DialogInterface, i: Int ->
        }
        alertDialogBuilder.setNegativeButton("Değiştir") { dialogInterface: DialogInterface, i: Int ->
            updateFood()
        }

        return alertDialogBuilder.create()
    }


    private fun removeDialog(fName : String) : AlertDialog{
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Besin Sil")
        alertDialogBuilder.setMessage("$fName besinini silmek istediğinden emin misin?")
        alertDialogBuilder.setPositiveButton("İptal") { dialogInterface: DialogInterface, i: Int -> }
        alertDialogBuilder.setNegativeButton("Sil") { dialogInterface: DialogInterface, i: Int ->
            removeFood()
        }

        return alertDialogBuilder.create()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == 16908332){
            onBackPressed()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}