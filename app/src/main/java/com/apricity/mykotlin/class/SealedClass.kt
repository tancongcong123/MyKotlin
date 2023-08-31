package com.apricity.mykotlin.`class`

/**
 * 密封类
 * 是一个限制类层次的类
 * 当对象具有来自有限集的类型之一，但不能具有任何其他类型的时候用密封类
 * 密封类的子类必须在密封类的同一文件中声明
 * 密封类通过尽在编译时限制类型集来确保类型安全
 * 密封类隐式是一个无法实例化的抽象类
 * 通常和when表达式一起使用
 * 和枚举的区别：密封类可以有子类
 */
sealed class SealedClass {
    class Circle(var radius: Float):SealedClass()
    class Square(var length: Int):SealedClass()
    class Rectangle(var length: Int, var breadth: Int):SealedClass()
    object NotAShape: SealedClass()
}

fun eval(e: SealedClass){
    when(e){
        is SealedClass.Circle-> println("Circle's area is ${3.14*e.radius*e.radius}")
        is SealedClass.Square-> println("Square's area is ${e.length*e.length}")
        is SealedClass.Rectangle-> println("Rectangle's area is ${e.length*e.breadth}")
        SealedClass.NotAShape->Double.NaN
//        else -> println("else case is not require as all case is covered above")
    }
}

fun main(args: Array<String>) {
    var c = SealedClass.Circle(3.0f)
    var s = SealedClass.Square(3)
    var r = SealedClass.Rectangle(3, 4)
    eval(c)
    eval(s)
    eval(r)
}