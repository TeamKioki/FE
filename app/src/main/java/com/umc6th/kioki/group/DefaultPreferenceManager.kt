package com.umc6th.kioki.group

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class DefaultPreferenceManager(private val context: Context) {
    companion object {
        private const val PREF_NAME = "default_preferences"
        private const val WORD_TEXT_SIZE = "text_size"
    }
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun setTextSize(size: Int) {
        editor.putInt(WORD_TEXT_SIZE, size).apply() // 사이즈의 값을 저장한다
    }

    fun getTextSize(): Int = sharedPreferences.getInt(WORD_TEXT_SIZE, 1) //기본값은 중간이다.

}