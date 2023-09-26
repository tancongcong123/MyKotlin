package com.apricity.mykotlin.flow

object HttpUtil {
    fun sendHttpRequest(param:String, callback: HttpCallback){
        println("send request")
        if (param=="aa"){
            callback.onFail("error~~~")
        }else{
            callback.onSuccess(param)
        }
    }
}