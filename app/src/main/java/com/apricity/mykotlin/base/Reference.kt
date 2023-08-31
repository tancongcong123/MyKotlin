package com.apricity.mykotlin.base

// 类引用
val c1 = String::class
fun isPositive(x:Int)=x>0

val x = 5
var y=6

class A(val aa:Int,val bb:String)
fun main(args: Array<String>) {
    val num = listOf(-10, 5,-5, 3, 6)
    // 函数引用，可以在另外一个函数中当参数
    println(num.filter(::isPositive))
    // 属性引用
    println(::x.get())
    println(::x.name)
    println(::y.set(10))

    val prop = A::bb
    println(prop.get(A(7, "awesome")))
}