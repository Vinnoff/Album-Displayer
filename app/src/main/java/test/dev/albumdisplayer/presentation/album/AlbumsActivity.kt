package test.dev.albumdisplayer.presentation.album

import org.koin.androidx.viewmodel.ext.android.viewModel
import test.dev.albumdisplayer.R
import test.dev.albumdisplayer.presentation.BaseActivity
import test.dev.albumdisplayer.presentation.album.list.TrackListFragment

class AlbumsActivity : BaseActivity(R.layout.albums_activty) {
    private val albumsViewModel: AlbumsViewModel by viewModel()
    private val listFragment: TrackListFragment by lazy { TrackListFragment() }

    override fun initUI() = Unit

    override fun initObserver() {
        albumsViewModel.liveDataNavigation.observe(this) { event ->
            event.getContentIfNotHandled()?.let { nav ->
                when (nav) {
                    AlbumsNavigation.LIST -> supportFragmentManager.beginTransaction()
                        .replace(R.id.albums_fragment_container, listFragment)
                        .commit()
                }
            }
        }
    }
}