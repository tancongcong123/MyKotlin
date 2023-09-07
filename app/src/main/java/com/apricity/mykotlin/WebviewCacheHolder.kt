package com.apricity.mykotlin

import android.app.Application
import android.content.Context
import android.content.MutableContextWrapper
import android.os.Looper
import android.webkit.WebView
import java.util.*

object WebviewCacheHolder {
    private val webviewCacheStack = Stack<WebView>()
    private const val CACHE_WEB_MAX_NUM = 4
    lateinit var application: Application

    fun init(application: Application){
        this.application = application
        prepareWebview()
    }
    fun prepareWebview(){
        if (webviewCacheStack.size< CACHE_WEB_MAX_NUM){
            Looper.myQueue().addIdleHandler {
                if (webviewCacheStack.size< CACHE_WEB_MAX_NUM){
                    webviewCacheStack.push(createWebview(MutableContextWrapper(application)))
                }
                false
            }
        }
    }

    fun aquireWebviewInternal(context: Context):WebView{
        if (webviewCacheStack.isEmpty()){
            return createWebview(context)
        }
        val webview = webviewCacheStack.pop()
        val contextWrapper = webview.context as MutableContextWrapper
        contextWrapper.baseContext = context
        return webview
    }

    private fun createWebview(context: Context):WebView{
        return WebView(context)
    }
}