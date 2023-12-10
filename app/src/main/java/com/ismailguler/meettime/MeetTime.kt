package com.ismailguler.meettime

import android.app.Application
import android.content.res.Configuration
import java.util.Locale

class MeetTime: Application() {
    override fun onCreate() {
        super.onCreate()
        setLocale()
    }

    private fun setLocale() {
        val locale = Locale("tr")
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}