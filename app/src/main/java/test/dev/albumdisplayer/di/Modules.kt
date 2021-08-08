package test.dev.albumdisplayer.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.facebook.stetho.okhttp3.StethoInterceptor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import test.dev.albumdisplayer.BuildConfig
import test.dev.albumdisplayer.data.local.LBCDao
import test.dev.albumdisplayer.data.local.LBCDatabase
import test.dev.albumdisplayer.data.local.source.LocalDataSource
import test.dev.albumdisplayer.data.remote.LBCService
import test.dev.albumdisplayer.data.remote.source.RemoteDataSource
import test.dev.albumdisplayer.data.repository.AlbumRepository
import test.dev.albumdisplayer.data.repository.AlbumRepositoryImpl
import test.dev.albumdisplayer.domain.GetAlbumListUseCase
import test.dev.albumdisplayer.presentation.album.AlbumsViewModel
import timber.log.Timber
import java.util.concurrent.TimeUnit

class Modules {
    companion object {
        val networkModule = module {
            single { createCache(androidContext()) }
            single { createOkHttpClient(get()) }
            single { createLBCWebService(get()) }
            single { RemoteDataSource(get()) }

        }

        val localModule = module {

            fun provideDatabase(application: Application) =
                Room.databaseBuilder(application, LBCDatabase::class.java, "lbc")
                    .fallbackToDestructiveMigration()
                    .build()

            fun provideCountriesDao(database: LBCDatabase) = database.lbcDao

            single { provideDatabase(androidApplication()) }
            single { provideCountriesDao(get()) }
            single { LocalDataSource(get()) }
        }

        val repositoryModule = module {
            single<AlbumRepository> { AlbumRepositoryImpl(get(), get()) }
        }

        val domainModule = module {
            factory { GetAlbumListUseCase(get()) }
        }

        val presentationModule = module {
            single<CoroutineDispatcher> { Dispatchers.Main }
            viewModel { AlbumsViewModel(get(), get()) }
        }
    }
}

fun createRoomDatabase(context: Context): LBCDatabase {
    return Room.databaseBuilder(
        context,
        LBCDatabase::class.java,
        "album_displayer.db",
    ).build()
}

fun provideDao(database: LBCDatabase): LBCDao {
    return database.lbcDao
}

fun createCache(context: Context): Cache {
    val cacheSize: Long = 10 * 1024 * 1024 //10Mo
    return Cache(context.cacheDir, cacheSize)
}

fun createOkHttpClient(cache: Cache): OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(10, TimeUnit.SECONDS)
    .addNetworkInterceptor(StethoInterceptor())
    .addNetworkInterceptor(HttpLoggingInterceptor { message -> Timber.tag("okhttp").d(message) }.apply { level = HttpLoggingInterceptor.Level.BODY })
    .build()

fun createLBCWebService(okHttpClient: OkHttpClient): LBCService {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.HOST)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create()
}
