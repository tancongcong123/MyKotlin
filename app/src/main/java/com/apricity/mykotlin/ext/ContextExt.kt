package com.apricity.mykotlin.ext

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.text.TextUtils

fun Context.openAccessibilitySetting() {
    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
}

fun Context.isAccessibilityOpened(serviceClass: Class<out AccessibilityService>): Boolean {
    val serviceName: String = this.applicationContext.packageName + "/" + serviceClass.canonicalName
    var accessibilityEnabled = 0
    try {
        accessibilityEnabled =
            Settings.Secure.getInt(this.contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED)
    } catch (e: Settings.SettingNotFoundException) {
        e.printStackTrace()
    }
    val ms = TextUtils.SimpleStringSplitter(':')
    if (accessibilityEnabled == 1) {
        val settingValue = Settings.Secure.getString(
            this.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )
        if (settingValue != null) {
            ms.setString(settingValue)
            while (ms.hasNext()) {
                val accessibilityService = ms.next()
                if (accessibilityService.equals(serviceName, ignoreCase = true)) {
                    return true
                }
            }
        }
    }
    return false
}