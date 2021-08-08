package test.dev.albumdisplayer.di.initializer

import android.content.Context
import androidx.startup.Initializer
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import test.dev.albumdisplayer.ABApplication
import test.dev.albumdisplayer.BuildConfig
import test.dev.albumdisplayer.di.Modules.Companion.domainModule
import test.dev.albumdisplayer.di.Modules.Companion.localModule
import test.dev.albumdisplayer.di.Modules.Companion.networkModule
import test.dev.albumdisplayer.di.Modules.Companion.presentationModule
import test.dev.albumdisplayer.di.Modules.Companion.repositoryModule

class KoinInitializer : Initializer<KoinApplication> {
    override fun create(context: Context): KoinApplication {
        return startKoin {
            androidContext(context)
            modules(
                listOf(
                    networkModule,
                    localModule,
                    repositoryModule,
                    domainModule,
                    presentationModule,
                )
            )
            if (BuildConfig.DEBUG) androidLogger()
        }.also { koinApplication -> ABApplication.koinApp = koinApplication }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(LoggerInitializer::class.java)
}