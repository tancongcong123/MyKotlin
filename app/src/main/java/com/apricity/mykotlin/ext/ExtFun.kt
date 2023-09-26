package com.apricity.mykotlin.ext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.apricity.mykotlin.MainActivity

/**
 * 以下都是kotlin的预定义扩展函数，它们都是对同一个对象进行多次操作的时候使用，使代码更加简洁
 *  函数	返回值	      调用者角色	        如何引用调用者
    also	调用者本身	  作为lambda参数	     it
    apply	调用者本身	  作为lambda接收者	 this
    let	    lambda返回值	  作为lambda参数	     it
    with	lambda返回值	  作为lambda接收者	 this
 */
class ExtFun {

    /**
     * apply接受一个lambda类型的参数block，且block的调用者是对象本身
     */
    fun start(context: Context){
        Intent(context, MainActivity::class.java).apply {
            action = "actionAAA"
            putExtras(Bundle().apply {
                putString("aa", "aa")
                putString("bb", "bb")
            })
            context.startActivity(this)
        }
    }
}