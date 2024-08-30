package com.umc6th.kioki.utils

import android.content.Context
import android.content.SharedPreferences

class GroupTextPrefs(context: Context) {
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    init {
        sharedPreference = context.getSharedPreferences("group-text", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()
    }
    fun setTextSize(isBig: Boolean) {
        editor.putBoolean("text", isBig).apply()
    }

    fun getTextSize() = sharedPreference.getBoolean("text", false)


    fun setTextColor(color: Int) {
        editor.putInt("group-color", color).apply()
    }

    fun getTextColor() = sharedPreference.getInt("group-color", 0xFFFFFFFF.toInt())

}