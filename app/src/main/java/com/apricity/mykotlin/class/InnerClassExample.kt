package com.apricity.mykotlin.`class`

/**
 * kotlin中的内部类
 * 内部类不能在接口和嵌套类中声明
 * 他是私有的，能访问外部类的成员，内部类保持对外部类对象的引用
 */
class InnerClassExample {
    private var name: String = "Afu"
    inner class InnerClass{
        var desc: String = "code in inner calss"
        private var id: Int = 1002
        fun foo(){
            println("name is $name") // access the outer class member even private
            println("Id is $id")
        }
    }
}

fun main(args: Array<String>) {
    println(InnerClassExample().InnerClass().desc)
    var obj = InnerClassExample().InnerClass()
    obj.foo()
}