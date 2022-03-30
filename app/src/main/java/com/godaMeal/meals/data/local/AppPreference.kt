package com.godaMeal.meals.data.local

import android.content.SharedPreferences


class AppPreference(private val preference: SharedPreferences) {

    fun getString(key: String, defaultValue: String): String =
        preference.getString(key, defaultValue)!!

    fun putString(key: String, value: String) = preference.edit().putString(key, value).apply()

    fun getInt(key: String, defaultValue: Int): Int = preference.getInt(key, defaultValue)

    fun putInt(key: String, value: Int) = preference.edit().putInt(key, value).apply()

    fun getBoolean(key: String, defaultValue: Boolean) = preference.getBoolean(key, defaultValue)

    fun putBoolean(key: String, value: Boolean) {
        preference.edit().putBoolean(key, value).apply()
    }

    fun clear() {
        preference.edit().clear().apply()
    }
}