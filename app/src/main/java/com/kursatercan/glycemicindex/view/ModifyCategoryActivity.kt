package com.kursatercan.glycemicindex.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kursatercan.glycemicindex.R
import com.kursatercan.glycemicindex.RealmDBActionListenerReferences
import com.kursatercan.glycemicindex.databinding.ActivityModifyCategoryBinding
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.Category
import com.kursatercan.glycemicindex.model.CurrentCategory

class ModifyCategoryActivity : AppCompatActivity() {
    private lateinit var bind : ActivityModifyCategoryBinding
    private val category : Category = CurrentCategory.category!!
    private var itemPosition : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityModifyCategoryBinding.inflate(layoutInflater)
        setContentView(bind.root)
        supportActionBar?.title = "Kategori Düzenle"
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.gradient_main))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        itemPosition = intent.getIntExtra("position",0)

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
        DBManager(this).getRealm()?.executeTransaction {
            category.title = bind.etCategoryTitle.text.toString()
        }
        Toast.makeText(this, "Değişiklikler kaydedildi.", Toast.LENGTH_SHORT).show()
        RealmDBActionListenerReferences.categoryFragmentListener?.onModifiedCategory(category)

    }

    private fun removeCategory(){
        DBManager(this).removeCategory(category.cid)
        RealmDBActionListenerReferences.categoryFragmentListener?.onModifiedCategory(category)
        Toast.makeText(this, "Kategori başarıyla silindi.", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun saveDialog() : AlertDialog {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Kategori Bilgilerini Güncelle")
        alertDialogBuilder.setMessage("Kategori başlığını değiştirmek istiyor musunuz?")
        alertDialogBuilder.setPositiveButton("İptal") { _: DialogInterface, _: Int ->
        }
        alertDialogBuilder.setNegativeButton("Değiştir") { _: DialogInterface, _: Int ->
            updateCategory()
        }

        return alertDialogBuilder.create()
    }


    private fun removeDialog(ctitle : String) : AlertDialog {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Besin Sil")
        alertDialogBuilder.setMessage("$ctitle kategorisini silmek istediğinden emin misin? Aynı zamanda kategori altındaki bütün besinlerde silinecektir.")
        alertDialogBuilder.setPositiveButton("İptal") { _: DialogInterface, _: Int -> }
        alertDialogBuilder.setNegativeButton("Sil") { _: DialogInterface, _: Int ->
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