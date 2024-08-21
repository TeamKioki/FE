package com.umc6th.kioki.utils

import android.content.Context
import android.content.SharedPreferences

private const val ACCESS_TOKEN = "Access_Token"
private const val REFRESH_TOKEN = "Refresh_Token"

class TokenPrefs(context: Context) {

    private lateinit var sharedPreference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    init {
        sharedPreference = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()
    }

    fun setAccessToken(value: String) {
        editor.putString(ACCESS_TOKEN, value).apply()
    }

    fun getAccessToken(defValue: String) = sharedPreference.getString(ACCESS_TOKEN, defValue)

    fun setRefreshToken(value: String) {
        editor.putString(REFRESH_TOKEN, value).apply()
    }

    fun getRefreshToken(defValue: String) = sharedPreference.getString(REFRESH_TOKEN, defValue)

}