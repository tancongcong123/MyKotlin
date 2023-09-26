package com.apricity.mykotlin.flow

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

/**
 * runBlocking
 *      一定会阻塞主线程
 *      可以指定runBlocking的工作线程
 */
fun main(args: Array<String>) {
    println("flow应用场景")
    runBlocking {
        println("默认主协程"+Thread.currentThread().name)
        flowCount(listOf("1", "2", "3"))
    }
    /**
     * 报错Module with the Main dispatcher had failed to initialize. For tests Dispatchers.setMain from kotlinx-coroutines-test module can be used
     * 可能需要有ui才能使用
     */
//    runBlocking(Dispatchers.Main) {
//        println( "主协程"+Thread.currentThread().name)
//    }
    runBlocking(Dispatchers.IO) {
        println("IO线程"+Thread.currentThread().name)
        flowCount(listOf("a", "b", "c"))
    }
    GlobalScope.launch(Dispatchers.IO) {
        println("GlobalScope "+Thread.currentThread().name)
        flowCount(listOf("【", "】", "、"))
    }
    println("runBlocking会阻塞主线程，在123、abc输出完成才会输出")
    val job = runBlocking {
        "我是小白"
    }
    println("job====$job")
}

private suspend fun flowCount(data:List<String>){
    flow {
        // 定义流如何产生数据
        data.forEach {
            delay(1000)
            emit(it)
        }
    }.onStart { println("start--------------") }
        .collect {
        // 定义如何消费数据
        println("value=$it")
    }
}