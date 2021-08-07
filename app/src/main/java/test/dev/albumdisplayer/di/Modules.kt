package test.dev.albumdisplayer.di

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import test.dev.albumdisplayer.BuildConfig
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
        val rootModule = module {
            single<CoroutineDispatcher> { Dispatchers.Main }
            single { createCache(androidContext()) }
            single { createOkHttpClient(get()) }
        }

        val dataModule = module {
            single { RemoteDataSource(get()) }

            single<LBCService> { createLBCWebService(get()) }

            single<AlbumRepository> { AlbumRepositoryImpl(get()) }
        }

        val domainModule = module {
            factory { GetAlbumListUseCase(get()) }
        }

        val presentationModule = module {
            viewModel { AlbumsViewModel(get(), get()) }
        }
    }
}

fun createCache(context: Context): Cache {
    val cacheSize: Long = 10 * 1024 * 1024 //10Mo
    return Cache(context.cacheDir, cacheSize)
}

fun createOkHttpClient(cache: Cache): OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(10, TimeUnit.SECONDS)
    .cache(cache)
    .addNetworkInterceptor(StethoInterceptor())
    .addNetworkInterceptor(HttpLoggingInterceptor { message -> Timber.tag("okhttp").d(message) }.apply { level = HttpLoggingInterceptor.Level.BODY })
    .build()

inline fun <reified T> createLBCWebService(okHttpClient: OkHttpClient): T {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.HOST)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create()
}
