package com.kursatercan.glycemicindex.view.fragment.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.kursatercan.glycemicindex.RealmDBActionListenerReferences
import com.kursatercan.glycemicindex.databinding.ActivityEditBinding.inflate
import com.kursatercan.glycemicindex.databinding.DialogModifyCategoryBinding
import com.kursatercan.glycemicindex.databinding.DialogModifyFoodBinding
import com.kursatercan.glycemicindex.db.DBManager
import com.kursatercan.glycemicindex.model.Category
import com.kursatercan.glycemicindex.util.ListenerRef


class ModifyCategoryDialogFragment : DialogFragment() {
    private var bind: DialogModifyCategoryBinding? = null
    private val binding get() = bind!!
    private lateinit var mContext: Context
    private lateinit var db: DBManager

    fun newInstance(cid: String, position: Int): ModifyCategoryDialogFragment {
        val f = ModifyCategoryDialogFragment()
        val args = Bundle()
        args.putString("cid", cid)
        args.putInt("position", position)

        f.arguments = args
        return f
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DialogModifyCategoryBinding.inflate(inflater, container, false)
        //getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);
        mContext = requireActivity().baseContext
        db = DBManager(mContext)

        val cid = arguments?.getString("cid")
        val position = arguments?.getInt("position")
        val category = db.getCategoryObjWithCID(cid!!)


        bind?.apply {
            etCategoryTitle.setText(category.title)

            btnSave.setOnClickListener {
                if(etCategoryTitle.text.isEmpty()){
                    etCategoryTitle.error = "Bu alan boş bırakılamaz!"
                } else{
                    db.getRealm()?.executeTransaction {
                        category.title = etCategoryTitle.text.toString()
                    }
                    ListenerRef.modifyCategoryDialogListener?.onUpdateCategory(category,position!!)
                    Toast.makeText(mContext, "Değişiklikler kaydedildi.", Toast.LENGTH_SHORT).show()
                }
            }

            btnRemove.setOnClickListener {
                db.removeCategory(category.cid)
                RealmDBActionListenerReferences.categoryFragmentListener?.onModifiedCategory(category)

                ListenerRef.modifyCategoryDialogListener!!.onRemoveCategory(position!!)

                Toast.makeText(mContext, "Kategori başarıyla silindi.", Toast.LENGTH_SHORT).show()
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

