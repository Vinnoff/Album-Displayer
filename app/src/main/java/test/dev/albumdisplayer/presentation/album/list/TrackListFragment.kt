package test.dev.albumdisplayer.presentation.album.list

import android.content.res.Configuration
import androidx.recyclerview.widget.LinearSnapHelper
import kotlinx.android.synthetic.main.track_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import test.dev.albumdisplayer.R
import test.dev.albumdisplayer.common.utils.toPx
import test.dev.albumdisplayer.presentation.*
import test.dev.albumdisplayer.presentation.album.AlbumsViewModel
import test.dev.albumdisplayer.presentation.album.AlbumsViewState
import test.dev.albumdisplayer.presentation.album.list.adapter.FullListAdapter
import test.dev.albumdisplayer.presentation.album.list.adapter.HeaderAdapter
import test.dev.albumdisplayer.presentation.album.list.adapter.TrackListAdapter

class TrackListFragment : BaseFragment(R.layout.track_list_fragment) {
    private val albumsViewModel: AlbumsViewModel by sharedViewModel()
    private val fullListAdapter: FullListAdapter by lazy { FullListAdapter() }
    private val headerAdapter: HeaderAdapter by lazy { HeaderAdapter() }
    private val trackListAdapter: TrackListAdapter by lazy { TrackListAdapter() }

    override fun initUI() {
        linkAdapters()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        linkAdapters()
    }

    private fun linkAdapters() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            tracklist_rv.adapter = trackListAdapter
            tracklist_rv.addItemDecoration(SpaceItemDecoration(spacing = 20.toPx(requireContext())))
            albums_rv.adapter = headerAdapter
            albums_rv.attachSnapHelperWithListener(LinearSnapHelper(), SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL_STATE_IDLE, object : OnSnapPositionChangeListener {
                override fun onSnapPositionChange(position: Int) {
                    albumsViewModel.onAlbumSelected(position)
                }
            })
        } else albums_rv.adapter = fullListAdapter
    }

    override fun initObserver() {
        albumsViewModel.liveDataAlbumList.observe(this) { viewState ->
            showLoader(viewState is AlbumsViewState.LOADER)
            when (viewState) {
                is AlbumsViewState.EMPTY -> showError()
                is AlbumsViewState.ERROR -> showError()
                is AlbumsViewState.SUCCESS -> {
                    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                        handleDataHorizontal(viewState.dataHorizontal)
                    else handleDataVertical(viewState.dataVertical.keys)
                }
            }
        }

        albumsViewModel.liveDataTrackList.observe(this) {
            trackListAdapter.submitList(it)
        }
    }

    private fun handleDataVertical(entries: Set<Int>) {
        headerAdapter.submitList(entries.toList())
        albumsViewModel.onAlbumSelected(0)
    }

    private fun handleDataHorizontal(dataHorizontal: List<AlbumView>) {
        fullListAdapter.submitList(dataHorizontal)
    }
}