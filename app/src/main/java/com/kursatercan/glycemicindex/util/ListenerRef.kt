package com.kursatercan.glycemicindex.util

import com.kursatercan.glycemicindex.adapter.ModifyCategoryDialogActions
import com.kursatercan.glycemicindex.adapter.ModifyFoodDialogActions
import com.kursatercan.glycemicindex.view.fragment.CategoriesFragmentListener

object ListenerRef{
    var modifyFoodDialogListener: ModifyFoodDialogActions? = null
    var modifyCategoryDialogListener: ModifyCategoryDialogActions? = null
    var categoriesFragmentRef: CategoriesFragmentListener? = null
}