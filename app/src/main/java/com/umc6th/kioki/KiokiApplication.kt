package com.umc6th.kioki

import android.app.Application
import com.umc6th.kioki.utils.TokenPrefs

class KiokiApplication : Application() {
    companion object {
        lateinit var tokenPrefs: TokenPrefs
    }

    override fun onCreate() {
        super.onCreate()

        tokenPrefs = TokenPrefs(applicationContext)

    }
}