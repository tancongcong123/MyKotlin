package com.apricity.mykotlin.accessibility

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.os.Build
import android.view.accessibility.AccessibilityEvent
import androidx.annotation.RequiresApi

class TestAccessibilityService:AccessibilityService() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        var testAccessibilityService: AccessibilityService?=null
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreate() {
        super.onCreate()
        testAccessibilityService = this

        (testAccessibilityService as TestAccessibilityService).rootInActiveWindow.printNodeInfo()
    }

    override fun onDestroy() {
        testAccessibilityService = null
        super.onDestroy()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        TODO("Not yet implemented")
    }

    override fun onInterrupt() {
        TODO("Not yet implemented")
    }
}