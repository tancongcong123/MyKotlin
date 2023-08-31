package com.apricity.mykotlin.delegate

import java.lang.Exception

class Example{
    var p:String by PDelegate()
}

val lazyValue:String by lazy {
    println("computed")
    throw Exception("errrror")
    "hello"
}
/**
 * 属性委托
 * 一个类中的属性不在类中直接定义，而是委托代理类定义，从而实现对该类的属性的统一管理
 */
fun main(args: Array<String>){
    var e = Example()
    print(e.p)

    e.p = "Runnnnn"
    println(e.p)

    try {
        println(lazyValue)
    }catch (e: Exception){
        e.printStackTrace()
    }
    println(lazyValue)
}
