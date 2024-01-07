package com.gaurav.findbook

import android.app.Application
import com.gaurav.findbook.data.AppContainer
import com.gaurav.findbook.data.DefaultAppContainer

class FindBookApplication:Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container = DefaultAppContainer()
    }
}