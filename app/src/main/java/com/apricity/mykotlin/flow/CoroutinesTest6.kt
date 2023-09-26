package com.apricity.mykotlin.flow

import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 使用协程简化回调方法
 */
fun main(args: Array<String>) {
    runBlocking {
        getBaiduRes()
        getAa()
    }
}

suspend fun getAa(){
    try {
        val res = request("aa")
        println(res)
    } catch (e:Exception){
    }
}
suspend fun getBaiduRes(){
    try {
        val res = request("http://www.baidu.com")
        println(res)
    } catch (e:Exception){
    }
}

suspend fun request(param:String):String{
    return suspendCoroutine {
        HttpUtil.sendHttpRequest(param, object : HttpCallback{
            override fun onSuccess(response: String) {
                it.resume(response)
            }

            override fun onFail(error: String) {
                it.resume(error)
//                it.resumeWithException(Throwable(error))
            }
        })
    }
}