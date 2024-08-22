package com.umc6th.kioki.utils

import android.content.Context
import android.content.SharedPreferences

class TextPrefs(context: Context) {

    private lateinit var sharedPreference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    init {
        sharedPreference = context.getSharedPreferences("text", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()
    }

    fun setTextSize(isBig: Boolean) {
        editor.putBoolean("text", isBig).apply()
    }

    fun getTextSize() = sharedPreference.getBoolean("text", false)

}