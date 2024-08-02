package com.daniel.helloworld.pertemuan11

import android.content.Context
import android.content.SharedPreferences

class MySharedPref(val context: Context) {

    private var pref: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveName(value: String) {
        val editor = pref.edit()
        editor?.putString(KEY_NAME, value)
        editor?.apply()
    }

    fun getName() : String? {
        return pref.getString(KEY_NAME, "-")
    }

    companion object {
        const val PREF_NAME = "my_pref"
        const val KEY_NAME = "name"

    }
}