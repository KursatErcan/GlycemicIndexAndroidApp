package com.kursatercan.glycemicindex.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kursatercan.glycemicindex.databinding.ActivityModifyCategoryBinding
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.Category
import com.kursatercan.glycemicindex.model.CurrentCategory

class ModifyCategoryActivity : AppCompatActivity() {
    private lateinit var bind : ActivityModifyCategoryBinding
    private val category : Category = CurrentCategory.category!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityModifyCategoryBinding.inflate(layoutInflater)
        setContentView(bind.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)


        bind.apply {
            etCategoryTitle.setText(category.title)

            btnSave.setOnClickListener {
                if(etCategoryTitle.text.isEmpty()){
                    etCategoryTitle.error = "Bu alan boş bırakılamaz!"
                } else{
                    saveDialog().show()

                }
            }

            btnRemove.setOnClickListener {
                removeDialog(category.title).show()
            }
        }
    }

    private fun updateCategory(){
        // TODO recyclerı güncelle

        DBManager(this).getRealm()?.executeTransaction {
            category.title = bind.etCategoryTitle.text.toString()
        }
        Toast.makeText(this, "Değişiklikler kaydedildi.", Toast.LENGTH_SHORT).show()

    }

    private fun removeCategory(){
        DBManager(this).removeCategory(category.cid)
        Toast.makeText(this, "Kategori başarıyla silindi.", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun saveDialog() : AlertDialog {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Kategori Bilgilerini Güncelle")
        alertDialogBuilder.setMessage("Kategori başlığını değiştirmek istiyor musunuz?")
        alertDialogBuilder.setPositiveButton("İptal") { dialogInterface: DialogInterface, i: Int ->
        }
        alertDialogBuilder.setNegativeButton("Değiştir") { dialogInterface: DialogInterface, i: Int ->
            updateCategory()
        }

        return alertDialogBuilder.create()
    }


    private fun removeDialog(ctitle : String) : AlertDialog {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Besin Sil")
        alertDialogBuilder.setMessage("$ctitle kategorisini silmek istediğinden emin misin? Aynı zamanda kategori altındaki bütün besinlerde silinecektir.")
        alertDialogBuilder.setPositiveButton("İptal") { dialogInterface: DialogInterface, i: Int -> }
        alertDialogBuilder.setNegativeButton("Sil") { dialogInterface: DialogInterface, i: Int ->
            removeCategory()
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