package com.apricity.mykotlin.base

/**
 * 泛型
 * 接受单个类中不同类型的参数
 * 语法
 *      class_or_interface<Type>
 *      <Type>methodName(parameter:classType<Type>)
 */
class Generic {
}
//泛型类
class Person<T>(age: T){
    init {
        println(age)
    }
}
//泛型方法
fun <T>printValue(list: ArrayList<T>){
    for (e in list){
        println(e)
    }
}

fun main(args: Array<String>) {
    Person(30)
    Person("this is age")

    printValue(arrayListOf("Cat", "Bird"))
    printValue(arrayListOf(2,4,1,3))
}