package com.kursatercan.glycemicindex.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.kursatercan.glycemicindex.adapter.CategorySpinnerAdapter
import com.kursatercan.glycemicindex.databinding.DialogModifyFoodBinding
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.Category
import com.kursatercan.glycemicindex.model.Food
import com.kursatercan.glycemicindex.util.ListenerRef


class ModifyFoodDialogFragment : DialogFragment() {
    private var bind: DialogModifyFoodBinding? = null
    private val binding get() = bind!!
    private lateinit var mContext: Context
    private lateinit var db: DBManager

    fun newInstance(fid: String, position: Int): ModifyFoodDialogFragment {
        val f = ModifyFoodDialogFragment()
        val args = Bundle()
        args.putString("fid", fid)
        args.putInt("position", position)

        f.arguments = args
        return f
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DialogModifyFoodBinding.inflate(inflater, container, false)
        //getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);
        mContext = requireActivity().baseContext
        db = DBManager(mContext)
        val fid = arguments?.getString("fid")
        val position = arguments?.getInt("position")
        val food: Food = db.getFood(fid!!)
        val categoryList = db.getCategories()
        val categorySpinnerAdapter = CategorySpinnerAdapter(categoryList, mContext)


        bind?.apply {
            spinnerCategory.adapter = categorySpinnerAdapter
            spinnerCategory.setSelection(categoryFind(food.cid, categoryList))
            etFoodName.setText(food.name)
            etGlysemicIndex.setText(food.glysemicIndex.toString())
            etCarbohydrateAmount.setText(food.carbohydrateAmount)
            etCalorie.setText(food.calorie)

            btnSave.setOnClickListener {
                if (etFoodName.text.isEmpty()) etFoodName.error = "Bu alan boş bırakılamaz!"
                if (etGlysemicIndex.text.isEmpty()) etGlysemicIndex.error =
                    "Bu alan boş bırakılamaz!"
                if (etCarbohydrateAmount.text.isEmpty()) etCarbohydrateAmount.error =
                    "Bu alan boş bırakılamaz!"
                if (etCalorie.text.isEmpty()) etCalorie.error = "Bu alan boş bırakılamaz!"

                if (etFoodName.text.isNotEmpty()
                    && etGlysemicIndex.text.isNotEmpty()
                    && etCarbohydrateAmount.text.isNotEmpty()
                    && etCalorie.text.isNotEmpty()
                ) {
                    val selectedCategory = spinnerCategory.selectedItem as Category
                    db.getRealm()?.executeTransaction {
                        food.cid = selectedCategory.cid
                        food.name = etFoodName.text.toString()
                        food.glysemicIndex = etGlysemicIndex.text.toString().toInt()
                        food.carbohydrateAmount = etCarbohydrateAmount.text.toString()
                        food.calorie = etCalorie.text.toString()
                    }
                    ListenerRef.modifyFoodDialogListener!!.onUpdateFood(food,position!!)
                    Toast.makeText(mContext, "Değişiklikler kaydedildi.", Toast.LENGTH_SHORT).show()
                }
            }

            btnRemove.setOnClickListener {
                db.removeFoodWithFID(food.fid)
                ListenerRef.modifyFoodDialogListener!!.onRemoveFood(position!!)
                Toast.makeText(mContext, "Ürün başarıyla silindi.", Toast.LENGTH_SHORT).show()
                dismiss()
            }

            ivCancel.setOnClickListener {
                dismiss()
            }
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        //dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

    }


    private fun categoryFind(cid: String, categoryList: ArrayList<Category>): Int {
        for ((index, category) in categoryList.withIndex()) {
            if (category.cid == cid) return index
        }
        return 0
    }



}

