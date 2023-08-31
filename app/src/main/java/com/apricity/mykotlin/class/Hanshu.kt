package com.apricity.mykotlin.`class`

/**
 * 高阶函数：接受函数作为参数或返回函数或可以同时执行这两个函数的函数
 */
fun myFun(org:String, portal:String, fn:(String, String)->String):Unit{
    val res = fn(org, portal)
    println(res)
}
fun main(args:Array<String>) {
    val fn:(String, String)->String = {org, portal->"$org develop $portal"}
    myFun("aaOrg", "aaPortal", fn)
    myFun("bbOrg", "bbPortal") { org, portal -> "$org developed $portal" }
}