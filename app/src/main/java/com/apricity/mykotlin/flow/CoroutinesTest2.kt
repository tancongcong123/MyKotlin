package com.apricity.mykotlin.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 输出
 *      launch1
        launch2
        launch1 finished
        launch2 finished
 * launch只能在协程中使用，创建子协程
 * 如果外层作用域的协程结束了，子协程也会随之结束
 *
 * 协程是并行执行的，但是他们都运行在同一个线程中，是由编程语言来决定如何在多个协程之间进行调度的
 */
fun main(args: Array<String>) {
    runBlocking {
        launch {
            println("launch1")
            delay(1000)
            println("launch1 finished")
        }
        launch {
            println("launch2")
            delay(1000)
            println("launch2 finished")
        }
    }
}