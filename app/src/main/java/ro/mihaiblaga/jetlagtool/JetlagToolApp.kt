package ro.mihaiblaga.jetlagtool

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JetlagToolApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}