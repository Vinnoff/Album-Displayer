package test.dev.albumdisplayer.presentation.album

import kotlinx.android.synthetic.main.albums_activty.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import test.dev.albumdisplayer.R
import test.dev.albumdisplayer.presentation.BaseActivity
import timber.log.Timber

class AlbumsActivity : BaseActivity(R.layout.albums_activty) {
    private val albumsViewModel: AlbumsViewModel by viewModel()
    private val albumAdapter: AlbumAdapter by lazy { AlbumAdapter() }
    override fun initUI() {
        albums_rv.adapter = albumAdapter
    }

    override fun initObserver() {
        albumsViewModel.liveDataAlbumList.observe(this) { viewState ->
            showLoader(viewState is AlbumsViewState.LOADER)
            when (viewState) {
                is AlbumsViewState.EMPTY -> showError()
                is AlbumsViewState.ERROR -> showError()
                is AlbumsViewState.SUCCESS -> handleData(viewState.data)
            }

        }
    }

    private fun handleData(data: List<AlbumView>) {
        albumAdapter.submitList(data)
        Timber.d(data.toString())
    }
}