package com.apricity.mykotlin.delegate

/**
 * 创建接口
 */
interface Base{
    fun print()
}

/**
 * 实现上面接口的被委托的类
 */
class BaseImpl(private val x:Int):Base{
    override fun print() {
        print("print by delegate class $x")
    }
}

/**
 * 创建委托类
 * 通过by关键字创建委托关系
 * Derived通过传入的对象b实现Base的方法
 */
class Derived(b:Base):Base by b

/**
 * 派生类Derived继承了接口Base的所有方法，并委托传入的对象实现Base的所有方法
 */
fun main(args: Array<String>){
    val b= BaseImpl(22)
    Derived(b).print()
}