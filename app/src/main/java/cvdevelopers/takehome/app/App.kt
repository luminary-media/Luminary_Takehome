package cvdevelopers.takehome.app

import android.app.Application
import cvdevelopers.takehome.koin.initKoin
import cvdevelopers.takehome.rx.config.initRx

class App : Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        initKoin()
        initRx()
    }
}
