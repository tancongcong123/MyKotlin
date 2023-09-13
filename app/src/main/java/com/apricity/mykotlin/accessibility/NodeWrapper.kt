package com.apricity.mykotlin.accessibility

import android.view.accessibility.AccessibilityNodeInfo

data class NodeWrapper(
    var className: String,
    var text: CharSequence? = null,
    var id: String? = null,
    var description: String? = null,
    var isClickable: Boolean = false,
    var isScrollable: Boolean = false,
    var isEditable: Boolean = false,
    var isSelected: Boolean = false,
    var isChecked: Boolean = false,
    var nodeInfo: AccessibilityNodeInfo? = null
){
    override fun toString() =
        "className = $className → text = $text → id = $id → description = $description → isClickable = $isClickable → isScrollable = $isScrollable → isEditable = $isEditable"

    fun toSimpleString(): String{
        var ss = StringBuilder()
        ss.append("className = $className")
        if (text.isNullOrBlank().not()){
            ss.append("text = $text")
        }
        if (id.isNullOrBlank().not()){
            ss.append("id = $id")
        }
        if (description.isNullOrBlank().not()){
            ss.append("description = $description")
        }
        if (isClickable){
            ss.append("isClickable = $isClickable")
        }
        if (isScrollable){
            ss.append("isScrollable = $isScrollable")
        }
        if (isEditable){
            ss.append("isEditable = $isEditable")
        }
        if (isSelected){
            ss.append("isSelected = $isSelected")
        }
        if (isChecked){
            ss.append("isChecked = $isChecked")
        }
        return ss.toString()
    }
}
