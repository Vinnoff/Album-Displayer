package test.dev.albumdisplayer.presentation.album

import kotlinx.android.synthetic.main.albums_activty.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import test.dev.albumdisplayer.R
import test.dev.albumdisplayer.domain.entity.AlbumData
import test.dev.albumdisplayer.presentation.BaseActivity
import timber.log.Timber

class AlbumsActivity : BaseActivity(R.layout.albums_activty) {
    private val albumsViewModel: AlbumsViewModel by viewModel()
    override fun initUI() {

    }

    override fun initObserver() {
        albumsViewModel.liveDataAlbumList.observe(this) { viewState ->
            showLoader(viewState is AlbumsViewState.LOADER)
            when (viewState) {
                AlbumsViewState.EMPTY -> showError()
                AlbumsViewState.ERROR -> showError()
                is AlbumsViewState.SUCCESS -> handleData(viewState.data)
            }

        }
    }

    private fun handleData(data: Map<Int, List<AlbumData>>) {
        albums_temp_display.text = data.toString()
        Timber.d(data.toString())
    }
}