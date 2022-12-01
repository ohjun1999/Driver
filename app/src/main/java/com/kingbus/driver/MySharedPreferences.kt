package com.kingbus.driver

import android.content.Context
import android.content.SharedPreferences

object MySharedPreferences {
    private val USER_ID: String = "uid"
    private val USER_SUBMIT: String = "submit"
    private val USER_NAME: String = "name"
    private val USER_LOGIN: String = "login"
    private val USER_IMAGE: String = "image"


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
    fun setSubmit(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_SUBMIT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_UID", input)
        editor.apply()
    }
    fun getSubmit(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_SUBMIT, Context.MODE_PRIVATE)
        return prefs.getString("MY_UID", "").toString()
    }
    fun setName(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_UID", input)
        editor.apply()
    }
    fun getName(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE)
        return prefs.getString("MY_UID", "").toString()
    }
    fun setLogin(context: Context, input: String?) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_LOGIN, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_UID", input)
        editor.apply()
    }
    fun getLogin(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_LOGIN, Context.MODE_PRIVATE)
        return prefs.getString("MY_UID", "").toString()
    }
    fun setImage(context: Context, input: String?) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_IMAGE, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_UID", input)
        editor.apply()
    }
    fun getImage(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_IMAGE, Context.MODE_PRIVATE)
        return prefs.getString("MY_UID", "").toString()
    }



}