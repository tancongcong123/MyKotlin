package com.apricity.mykotlin.accessibility

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class AutoAccessibilityService:AsyncAccessibilityService() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        var autoAccessibilityService: AccessibilityService?=null
    }

    override fun targetPackageName(): String =""

    override fun asyncHandleAccessibilityEvent(event: AccessibilityEvent) {
        Log.d("as", "asyncHandleAccessibilityEvent "+event.eventType)
    }

    override fun onCreate() {
        super.onCreate()
        autoAccessibilityService = this
    }

    override fun onDestroy() {
        autoAccessibilityService = null
        super.onDestroy()
    }
}