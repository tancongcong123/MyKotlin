package com.apricity.mykotlin.flow

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CoroutineTest {
}

fun main(args: Array<String>) {
    runBlocking {
        launch(Dispatchers.Default+CoroutineName("子协程")) {
            println(this.coroutineContext.toString())
        }
    }
}