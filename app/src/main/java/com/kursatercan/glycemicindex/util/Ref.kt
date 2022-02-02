package com.kursatercan.glycemicindex.util

import com.kursatercan.glycemicindex.adapter.ModifyCategoryDialogActions
import com.kursatercan.glycemicindex.adapter.ModifyFoodDialogActions

object ListenerRef{
    var modifyFoodDialogListener: ModifyFoodDialogActions? = null
    var modifyCategoryDialogListener: ModifyCategoryDialogActions? = null
}