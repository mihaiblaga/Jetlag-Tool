package ro.mihaiblaga.jetlagtool

import android.app.Application
import ro.mihaiblaga.jetlagtool.core.di.AppModule
import ro.mihaiblaga.jetlagtool.core.di.AppModuleImpl

class JetlagTool : Application() {
    lateinit var appModule: AppModule

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}