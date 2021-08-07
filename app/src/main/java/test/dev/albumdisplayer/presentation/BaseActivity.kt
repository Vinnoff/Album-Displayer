package test.dev.albumdisplayer.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleOwner
import test.dev.albumdisplayer.BuildConfig

abstract class BaseActivity(@LayoutRes val layoutRes: Int) : AppCompatActivity(), LifecycleOwner {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
    }

    override fun onStart() {
        super.onStart()
        initUI()
        initObserver()
    }

    abstract fun initUI()
    abstract fun initObserver()
    open fun showLoader(isLoading: Boolean) {
        if (BuildConfig.DEBUG) Toast.makeText(this, "Loading : $isLoading", Toast.LENGTH_SHORT).show()
    }

    open fun showError(errorText: String? = null) {
        if (BuildConfig.DEBUG) Toast.makeText(this, errorText ?: "Error", Toast.LENGTH_SHORT).show()
    }
}

fun BaseActivity.sendEmail(receiver: String) {
    val uri = "mailto:$receiver".toUri()
    startActivity(Intent(Intent.ACTION_SENDTO, uri))
}

fun BaseActivity.callNumber(number: String) {
    val uri = "tel:$number".toUri()
    startActivity(Intent(Intent.ACTION_DIAL, uri))
}

fun BaseActivity.displayMap(street: String, city: String, country: String) {
    val location = "$street $city $country".replace(' ', '+')
    val uri = "geo:0,0?q=$location".toUri()
    startActivity(Intent(Intent.ACTION_VIEW, uri))
}