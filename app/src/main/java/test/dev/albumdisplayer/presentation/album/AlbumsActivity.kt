package test.dev.albumdisplayer.presentation.album

import kotlinx.android.synthetic.main.albums_activty.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import test.dev.albumdisplayer.R
import test.dev.albumdisplayer.presentation.BaseActivity

class AlbumsActivity : BaseActivity(R.layout.albums_activty) {
    private val albumsViewModel: AlbumsViewModel by viewModel()
    override fun initUI() {

    }

    override fun initObserver() {
        albumsViewModel.liveDataAlbumList.observe(this) {
            albums_temp_display.text = it.toString()
        }
    }

}