package com.ismailguler.meettime

import android.content.Context
import android.content.SharedPreferences
import java.util.Locale

class SharedPreferencesUtil(private val context: Context) {
    companion object {
        private const val USER_LIST_KEY = "USER_LIST_KEY"
        private val defaultUsers = mutableListOf<String>("İsmail", "Ali", "Süleyman", "Hasan")
        const val LAST_SELECTED_USER = "LAST_SELECTED_USER"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MeetTimeSharedPreferences", Context.MODE_PRIVATE)

    fun saveStringList(key: String, stringList: List<String>) {
        val stringSet = HashSet<String>(stringList)
        sharedPreferences.edit().putStringSet(key, stringSet).apply()
    }

    fun getStringList(key: String): MutableList<String> {
        val stringSet = sharedPreferences.getStringSet(key, HashSet()) ?: HashSet()
        return ArrayList(stringSet)
    }

    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun clearSharedPreferences() {
        sharedPreferences.edit().clear().apply()
    }

    fun getUsers(): MutableList<String> {
        var userList = getStringList(USER_LIST_KEY)
        if (userList.isEmpty()) {
            saveStringList(USER_LIST_KEY, defaultUsers)
            userList = defaultUsers
        }
        userList.sortBy { it.lowercase(Locale("tr")) }
        return userList
    }

    fun addNewUser(userName: String) {
        val users = getUsers()
        if (!users.contains(userName)) {
            users.add(userName)
            saveStringList(USER_LIST_KEY, users)
        }
    }
}