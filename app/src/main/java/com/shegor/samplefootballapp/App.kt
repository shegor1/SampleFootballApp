package com.shegor.samplefootballapp

import android.app.Application
import com.onesignal.OneSignal
import com.shegor.samplefootballapp.persistantStorage.FootballDatabase

lateinit var footballDb: FootballDatabase
const val ONESIGNAL_APP_ID = "c5712a14-513b-4ab6-b5b4-203670c4ccea"

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        footballDb = FootballDatabase.getInstance(this)
        instance = this

    }

    fun setupOneSignal() {
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }

}