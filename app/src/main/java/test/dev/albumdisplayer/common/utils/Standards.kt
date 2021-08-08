package test.dev.albumdisplayer.common.utils

import timber.log.Timber
import java.net.InetAddress

fun logException(throwable: Throwable) {
    Timber.e(throwable)
}

fun isInternetAvailable() = try {
    val ipAddr = InetAddress.getByName("google.com")
    !ipAddr.equals("")
} catch (e: Exception) {
    false
}