package com.apricity.mykotlin.`class`

/**
 * 内联函数：使用内联关键字声明，内联函数的使用增强了高阶函数的性能。内联函数告诉编译器将函数和参数复制到调用站点
 * 虚函数或局部函数不能声明为内联函数
 * 内联函数内部不支持的一些表达式和声明：
 *      局部类声明
 *      内部嵌套类的声明
 *      函数表达式
 *      声明局部函数 局部可选参数的默认值
 */
fun main() {
    inlineFun { println("调用内联函数") }
}

inline fun inlineFun(myFun:()->Unit){
    myFun()
    println("内联函数内的代码")
}