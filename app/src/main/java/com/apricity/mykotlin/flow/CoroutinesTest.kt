package com.apricity.mykotlin.flow

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * GlobalScope.launch每次创建一个顶层协程，这种协程当程序运行结束也随之结束
 * delay只会挂起当前协程，不会影响其他协程的运行
 * 以下代码输出：
 *      run in coroutines scope

        Process finished with exit code 0
 *  因为协程挂起1000ms，但是线程只阻塞500ms，第二个打印还来不及执行应用程序就结束了
 *  runBlocking也已解决上述问题，但是runBlocking只适合在测试环境使用，线上使用的话会产生性能问题
 */
fun main(args: Array<String>) {
    GlobalScope.launch {
        println("run in coroutines scope")
        delay(1000)
        println("run in coroutines scope finished")
    }
    Thread.sleep(500)
}