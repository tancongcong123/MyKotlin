package com.apricity.mykotlin.base

fun main(args: Array<String>) {
    val str1 = """Kotlin is official language 
    |announce by Google for  
    |android application development"""
    println(str1)
    // 删除前导空格
    val str2 = """Kotlin is official language 
    |announce by Google for  
    |android application development""".trimMargin()
    println(str2)
}