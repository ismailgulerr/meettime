package com.ismailguler.meettime

import android.content.Context
import android.content.SharedPreferences
import java.util.Locale

class SharedPreferencesUtil(private val context: Context) {
    companion object {
        private const val USER_LIST_KEY = "USER_LIST_KEY"
        private val defaultUsers = mutableListOf<String>("İsmail", "Ali", "Süleyman", "Hasan")
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
        users.add(userName)
        saveStringList(USER_LIST_KEY, users)
    }
}