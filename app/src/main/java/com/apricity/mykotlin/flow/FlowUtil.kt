package com.apricity.mykotlin.flow

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

/**
 * runBlocking
 *      创建主协程
 *      可以指定工作线程
 *      使用一定会阻塞主线程
 * launch
 *      GlobalScope.launch创建主协程
 *      runBlocking创建主协程（在内部创建launch{}子协程）
 * CoroutineScope
 *      可以开启一个协程，并且不会阻塞主线程
 *      通过CoroutineScope.launch开启一个协程，里面任务会挂起，让后面的代码先执行，直到协程体内部方法执行完成在自动切回所在的上下文回调结果
 *      可以指定工作线程
 * withContext
 *      不创建新的协程，指定协程上运行的代码块，可以切换到指定线程并在闭包内逻辑执行完毕后自动切换线程继续执行
 *      必须在协程或者suspend函数中调用
 *      通过Dispatchers指定代码块运行的线程
 *      会阻塞上下文线程
 *      有返回值，会返回代码块的最后一行的值
 * async
 *      创建带返回值的协程，返回的是Deferred类
 *      一定要用async .... await来取返回数据
 *      async后面紧跟着await的话效果跟withContext一样
 */
class FlowUtil {

    fun runFlow(){
        runBlocking {
            println("默认主协程"+Thread.currentThread().name)
            flowCount(listOf("1", "2", "3"))
        }
        /**
         * 报错Module with the Main dispatcher had failed to initialize. For tests Dispatchers.setMain from kotlinx-coroutines-test module can be used
         * 可能需要有ui才能使用
         */
//        runBlocking {
//            withContext(Dispatchers.Main){
//                println( "主协程"+Thread.currentThread().name)
//            }
//        }
        runBlocking(Dispatchers.IO) {
            println("IO线程"+Thread.currentThread().name)
            flowCount(listOf("a", "b", "c"))
        }
        GlobalScope.launch {
            println("GlobalScope "+Thread.currentThread().name)
            flowCount(listOf("【", "】", "、"))
        }
        CoroutineScope(Dispatchers.Main).launch {
            Log.d("CoroutineScope", "开启一个协程并且不会阻塞主线程")
        }
        println("runBlocking会阻塞主线程，在123、abc输出完成才会输出")
        val job = runBlocking {
            "我是小白"
        }
        println("job====$job")
        CoroutineScope(Dispatchers.Main).launch {
            val time = System.currentTimeMillis()
            val task1 = withContext(Dispatchers.IO){
                delay(500)
                Log.e("TTT withContext", "执行task1，当前线程="+Thread.currentThread().name)
                "one"
            }
            val task2 = withContext(Dispatchers.IO){
                delay(500)
                Log.e("TTT withContext", "执行task2，当前线程="+Thread.currentThread().name)
                "two"
            }
            Log.d("TTT withContext", "task1=$task1;task2=$task2;耗时=${System.currentTimeMillis()-time}ms;当前线程=${Thread.currentThread().name}")
        }
        CoroutineScope(Dispatchers.Main).launch {
            val time = System.currentTimeMillis()
            val task1 = async(Dispatchers.IO){
                delay(1000)
                Log.e("TTT async", "执行task1，当前线程="+Thread.currentThread().name)
                "async one"
            }
            val task2 = async(Dispatchers.IO){
                delay(1000)
                Log.e("TTT async", "执行task2，当前线程="+Thread.currentThread().name)
                "async two"
            }
            Log.d("TTT async", "task1=${task1.await()};task2=${task2.await()};耗时=${System.currentTimeMillis()-time}ms;当前线程=${Thread.currentThread().name}")
        }
    }

    private suspend fun flowCount(data:List<String>){
        flow {
            // 定义流如何产生数据
            data.forEach {
                delay(500)
                emit(it)
            }
        }.onStart { println("start--------------") }
            .collect {
                // 定义如何消费数据
                Log.d("TTT","value=$it")
            }
    }
}