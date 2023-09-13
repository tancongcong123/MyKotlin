package com.apricity.mykotlin.accessibility

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import java.util.concurrent.Executors

abstract class AsyncAccessibilityService :AccessibilityService() {

    private val executors = Executors.newSingleThreadExecutor()

    abstract fun targetPackageName(): String

    abstract fun asyncHandleAccessibilityEvent(event: AccessibilityEvent)

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val accessibilityEvent = event ?:return
        executors.run {
            asyncHandleAccessibilityEvent(event)
        }
    }

    override fun onInterrupt() {
        TODO("Not yet implemented")
    }
}