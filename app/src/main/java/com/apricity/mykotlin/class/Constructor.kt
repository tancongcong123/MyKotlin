package com.apricity.mykotlin.`class`

/**
 * 声明带有name和id的构造函数Constructor
 */
class Constructor(val name: String, var id: Int)

/**
 * 声明带有name和id的构造函数Constructor2 初始化块
 * 主构造函数不包含任何代码 初始化块用于初始化程序
 * 初始化块的执行顺序和他在类中出现的顺序相同
 */
class Constructor2(val name: String, var id: Int){
    private val _name: String = name
    private var _id: Int = id

    init {
        println("Constructor2 name=$_name, id=$_id")
    }
}

/**
 * 辅助构造函数
 * 类中可以创建一个或多个辅助构造函数，使用constructor声明
 */
class Constructor3{
    constructor(id: Int){
        println("id = $id, has no name")
    }
    constructor(name: String, id: Int){
        println("name = $name, id=$id")
    }
}

/**
 * 在同一个类中同时使用主构造函数和辅助构造函数
 * 辅助构造函数需要授权给主构造函数，使用this关键字授权
 * 一个辅助构造函数可以调用同一个类中的另一个辅助构造函数，通过this关键字完成
 */
class Constructor4(id: Int){
    init {
        println("this is init block")
    }
    constructor(name: String, id: Int):this(id){
        println("name = $name, id=$id")
    }
    constructor(name: String, id: Int, age: Int):this(name, id){
        println("name = $name, id=$id, age=$age")
    }
}

fun main(args: Array<String>) {
    val myConstructor = Constructor("Susan", 1002)
    println("name = ${myConstructor.name}, id = ${myConstructor.id}")

    Constructor2("Susu", 1003)

    Constructor3(1004)
    Constructor3("rongrong", 1004)

    Constructor4(1005)
    Constructor4("yuanyuan", 1007)
    Constructor4("lulu", 1008, 3)
}