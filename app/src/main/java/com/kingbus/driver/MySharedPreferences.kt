package com.kingbus.driver

import android.content.Context
import android.content.SharedPreferences

object MySharedPreferences {
    private val USER_ID: String = "uid"




    fun setUserUid(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_ID, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_UID", input)
        editor.apply()
    }
    fun getUserUid(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_ID, Context.MODE_PRIVATE)
        return prefs.getString("MY_UID", "").toString()
    }



}