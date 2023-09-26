package com.apricity.mykotlin.flow

import kotlinx.coroutines.*

suspend fun getInfo():String{
    delay(500)
    println("code after delay")
    return "hello coroutine"
}