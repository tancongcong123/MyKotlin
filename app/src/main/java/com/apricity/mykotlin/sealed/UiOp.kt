package com.apricity.mykotlin.sealed

import android.view.View

/**
 * 假如在 Android 中我们有一个 view，我们现在想通过 when 语句设置针对 view 进行两种操作：显示和隐藏
 * 在此基础上添加需求，对view进行水平垂直方向的平移，并且携带一些数据
 */
sealed class UiOp {
    object Show:UiOp()
    object Hide:UiOp()
    class TranslateX(val px:Float):UiOp()
    class TranslateY(val px:Float):UiOp()
}

fun execute(view: View, op: UiOp) = when(op){
    UiOp.Show -> view.visibility = View.VISIBLE
    UiOp.Hide -> view.visibility = View.GONE
    is UiOp.TranslateX -> view.translationX = op.px
    is UiOp.TranslateY -> TODO()
}