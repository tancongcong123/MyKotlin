package com.apricity.mykotlin.flow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 输出
 *      1
        2
        3
        4
        5
        6
        7
        8
        9
        10
        coroutineScope finished
        runBlocking finished
 *
 * coroutinesScope和runBlocking功能类似，都能保证作用域中和子协程运行完之前阻塞外部协程
 * ，但是runBlocking会阻塞外部线程，而coroutinesScope只会影响当前协程，不会有性能问题
 */
fun main(args: Array<String>) {
    runBlocking {
        printNum()
        println("runBlocking finished")
    }
}

suspend fun printNum() = coroutineScope {
    launch {
        for (i in 1..10){
            println(i)
            delay(500)
        }
        println("coroutineScope finished")
    }
}