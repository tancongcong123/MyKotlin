package com.apricity.mykotlin.flow

import kotlinx.coroutines.*

/**
 * withContext是一个挂起函数，可以看成简化版的async
 * 调用withContext之后，会立即执行代码块，同时将外部协程挂起，当代码全部执行完之后，会把最后一行作为函数的返回值返回
 * 线程参数
 *      Dispatchers.Default 默认低并发，当执行计算密集型任务时，高并发会影响执行效率，适合使用低并发策略
 *      Dispatchers.IO 高并发线程策略，当执行代码需要阻塞和等待时候，比如网络请求，为了能支持更高的并发量，就适合使用这个设置
 *      Dispatchers.Main Android主线程中执行代码，只能在Android项目中使用，纯kotlin项目使用会报错
 */
fun main(args: Array<String>) {
    runBlocking {
        val res = withContext(Dispatchers.Default){
            5+6
        }
        println(res)
    }
}