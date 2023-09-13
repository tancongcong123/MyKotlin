package com.apricity.mykotlin.accessibility

import android.accessibilityservice.AccessibilityService
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
fun AccessibilityService?.printNodeInfo(simplePrint: Boolean):String{
    this ?:return ""
    return rootInActiveWindow.printNodeInfo(isSimplePrint = simplePrint)
}