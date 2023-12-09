package com.ismailguler.meettime

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil(private val context: Context) {
    companion object {
        private const val USER_LIST_KEY = "USER_LIST_KEY"
        private val defaultUsers = listOf("İsmail", "Ali", "Süleyman", "Hasan")
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MeetTimeSharedPreferences", Context.MODE_PRIVATE)

    fun saveStringList(key: String, stringList: List<String>) {
        val stringSet = HashSet<String>(stringList)
        sharedPreferences.edit().putStringSet(key, stringSet).apply()
    }

    fun getStringList(key: String): List<String> {
        val stringSet = sharedPreferences.getStringSet(key, HashSet()) ?: HashSet()
        return ArrayList(stringSet)
    }

    fun clearSharedPreferences() {
        sharedPreferences.edit().clear().apply()
    }

    fun getUsers(): List<String> {
        val userList = getStringList(USER_LIST_KEY)
        if (userList.isEmpty()) {
            saveStringList(USER_LIST_KEY, defaultUsers)
            return defaultUsers
        }
        return userList
    }
}