package com.apricity.mykotlin.accessibility

import android.os.Build
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi
import com.apricity.mykotlin.ext.default

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
fun AccessibilityNodeInfo?.findNodeWrapper(
    isPrint:Boolean = true,
    prefix: String = "",
    isLast: Boolean = false,
    compare:(NodeWrapper)->Boolean
): NodeWrapper? {
    val node = this ?:return null
    val nodeWrapper = NodeWrapper(
        className = node.className.default(),
        text = node.text.default(),
        id = node.viewIdResourceName.default(),
        description = node.contentDescription.default(),
        isClickable = node.isClickable,
        isScrollable = node.isScrollable,
        isEditable = node.isEditable,
        nodeInfo = node
    )
    if (compare(nodeWrapper)){
        return nodeWrapper
    }
    val maker = if (isLast) """\--- """ else """+--- """
    val currentPrefix = "$prefix$maker"
    if (isPrint){
        Log.d("printNodeInfo", currentPrefix+nodeWrapper.toString())
    }
    val size = node.childCount
    if (size>0){
        val childPrefix = prefix+ if (isLast) "  " else "|  "
        val lastChildIndex = size-1
        for (index in 0 until size){
            val isLastChild = index==lastChildIndex
            val find = node.getChild(index).findNodeWrapper(isPrint, childPrefix, isLastChild, compare)
            if (find!=null){
                return find
            }
        }
    }
    return null
}

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
fun AccessibilityNodeInfo?.printNodeInfo(
    isSimplePrint:Boolean = true,
    prefix: String = "",
    printContent: StringBuilder = StringBuilder(),
    isLast: Boolean = false
): String {
    val node = this ?:return printContent.toString()
    val nodeWrapper = NodeWrapper(
        className = node.className.default(),
        text = node.text.default(),
        id = node.viewIdResourceName.default(),
        description = node.contentDescription.default(),
        isClickable = node.isClickable,
        isScrollable = node.isScrollable,
        isEditable = node.isEditable,
        nodeInfo = node
    )
    val maker = if (isLast) """\--- """ else """+--- """
    val currentPrefix = "$prefix$maker"
    val print = currentPrefix + if (isSimplePrint) nodeWrapper.toSimpleString() else nodeWrapper.toString()
    printContent.append("$print \n")
    Log.d("printNodeInfo", print)
    val size = node.childCount
    if (size>0){
        val childPrefix = prefix+ if (isLast) "  " else "|  "
        val lastChildIndex = size-1
        for (index in 0 until size){
            val isLastChild = index==lastChildIndex
            node.getChild(index).printNodeInfo(isSimplePrint, childPrefix, printContent, isLastChild)
        }
    }
    return printContent.toString()
}