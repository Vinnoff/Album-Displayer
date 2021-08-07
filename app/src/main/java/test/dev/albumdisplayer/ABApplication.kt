package test.dev.albumdisplayer

import android.app.Application
import org.koin.core.KoinApplication

class ABApplication : Application() {

    companion object {
        lateinit var koinApp: KoinApplication
    }
}