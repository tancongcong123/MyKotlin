package com.apricity.mykotlin

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Debug
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.core.os.TraceCompat
import com.apricity.mykotlin.accessibility.AutoAccessibilityService
import com.apricity.mykotlin.accessibility.printNodeInfo
import com.apricity.mykotlin.ext.isAccessibilityOpened
import com.apricity.mykotlin.ext.openAccessibilitySetting

/**
 * navigation组件旨在用于具有一个主activity和多个fragment的应用。
 * 主activity和导航图相关联，包含一个导航宿主NavHostFragment，是一个空容器，用于指向目标fragment
 * 在具有多个activity的应用中，每一个activity都拥有自己的导航图
 */
class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Debug.startMethodTracing()
//        TraceCompat.beginSection("mainActivity onCreate")
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            println("-----------print node info")
            AutoAccessibilityService.autoAccessibilityService?.printNodeInfo(true)
        }
//        Debug.stopMethodTracing()
//        TraceCompat.endSection()
    }

    override fun onResume() {
        super.onResume()
        if(!baseContext.isAccessibilityOpened(AutoAccessibilityService::class.java)){
            baseContext.openAccessibilitySetting()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}