package com.apricity.mykotlin.sealed

import android.view.View

/**
 * 密封类用来表示受限的类继承结构：当一个值为有限的几种类型，而不能有其他类型时候。
 * 密封类可以有子类
 * 假如在 Android 中我们有一个 view，我们现在想通过 when 语句设置针对 view 进行两种操作：显示和隐藏
 *
 * 此时的需求可以用枚举替代
 */
sealed class UiOpSimple {
    object Show:UiOpSimple()
    object Hide:UiOpSimple()
}

fun execute(view: View, op:UiOpSimple) = when(op){
    UiOpSimple.Show -> view.visibility = View.VISIBLE
    UiOpSimple.Hide -> view.visibility = View.GONE
}