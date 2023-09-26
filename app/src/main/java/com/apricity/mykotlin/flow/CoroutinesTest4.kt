package com.apricity.mykotlin.flow

import kotlinx.coroutines.*

/**
 * 实际项目中一般如下使用
 */
fun main(args: Array<String>) {
    val job = Job()
    val scope = CoroutineScope(job)
    scope.launch {
        // 处理具体逻辑1
    }
    scope.launch {
        // 处理具体逻辑2
    }
    // 取消协程
    scope.cancel()
}