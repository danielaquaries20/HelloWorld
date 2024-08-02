package com.daniel.helloworld.mytest.shared_pref

import android.content.Context
import android.content.SharedPreferences

class MySession(context: Context) {

    private var pref: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveName(value: String) {
        val editor = pref.edit()
        editor?.putString(KEY_NAME, value)
        editor?.apply()
    }

    fun getName(): String? {
        return pref.getString(KEY_NAME, "-")
    }

    companion object {
        const val PREF_NAME = "my_session"
        const val KEY_NAME = "name"
    }
}