package com.apricity.mykotlin.`class`

/**
 * kotlin中的嵌套类
 * 嵌套类默认是静态的，因此可以在不创建类对象的情况下直接调用其数据成员和成员函数
 * 嵌套类无法访问外部类的数据成员
 */
class NestClassExample {
    private var _name: String  = "Ashu"
    var name = "Booo"
    class NestClass{
        var description: String = "code in nest class"
        private var id: Int = 1001
        fun foo(){
            // cannot access OuterClass's member
            // println("_name is $_name")
            // println("name is $name")
            println("Id is $id")
        }
    }
}

fun main(args: Array<String>) {
    println(NestClassExample.NestClass().description)
    var obj = NestClassExample.NestClass()
    obj.foo()
}