package com.apricity.mykotlin.flow

interface HttpCallback {
    fun onSuccess(response: String)
    fun onFail(error: String)
}