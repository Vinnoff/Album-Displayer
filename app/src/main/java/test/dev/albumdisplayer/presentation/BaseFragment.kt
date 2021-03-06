package test.dev.albumdisplayer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import test.dev.albumdisplayer.BuildConfig

abstract class BaseFragment(@LayoutRes val layoutRes: Int) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObserver()
    }

    // Must be overridden by all Fragments
    abstract fun initUI()
    abstract fun initObserver()
    open fun showLoader(isLoading: Boolean) {
        if (BuildConfig.DEBUG) Toast.makeText(requireContext(), "Loading : $isLoading", Toast.LENGTH_SHORT).show()
    }

    open fun showError(errorText: String = "Error") {
        if (BuildConfig.DEBUG) Toast.makeText(requireContext(), errorText, Toast.LENGTH_SHORT).show()
    }
}