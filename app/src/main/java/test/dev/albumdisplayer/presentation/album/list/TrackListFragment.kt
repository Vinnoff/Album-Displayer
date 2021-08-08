package test.dev.albumdisplayer.presentation.album.list

import android.content.res.Configuration
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import kotlinx.android.synthetic.main.track_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import test.dev.albumdisplayer.R
import test.dev.albumdisplayer.common.utils.load
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
        tracklist_fragment_loader.load(R.raw.best_loader_ever)
        header_next?.setOnClickListener { albumsViewModel.onAlbumSelected((albums_rv.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() + 1) }
        header_previous?.setOnClickListener { albumsViewModel.onAlbumSelected((albums_rv.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() - 1) }
        linkAdapters()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        linkAdapters()
    }

    private fun linkAdapters() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            tracklist_rv?.adapter = trackListAdapter
            tracklist_rv?.addItemDecoration(SpaceItemDecoration(spacing = 20.toPx(requireContext())))
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
            tracklist_fragment_error.isVisible = false
            showLoader(viewState is AlbumsViewState.LOADER)
            when (viewState) {
                is AlbumsViewState.EMPTY -> showError(getString(R.string.empty_list))
                is AlbumsViewState.ERROR -> showError(getString(R.string.generic_error))
                is AlbumsViewState.SUCCESS -> {
                    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                        handleDataHorizontal(viewState.dataHorizontal)
                    else handleDataVertical(viewState.dataVertical.keys)
                }
            }
        }

        albumsViewModel.liveDataTrackList.observe(this) { viewState ->
            albums_rv?.smoothScrollToPosition(viewState.position)
            header_previous?.isInvisible = !viewState.needToShowPrevious
            header_next?.isInvisible = !viewState.needToShowNext
            trackListAdapter.submitList(viewState.data)
        }
    }

    private fun handleDataVertical(entries: Set<Int>) {
        albums_rv?.isVisible = true
        tracklist_rv?.isVisible = true
        header_background?.isVisible = true
        headerAdapter.submitList(entries.toList())
        albumsViewModel.onAlbumSelected(0)
    }

    private fun handleDataHorizontal(dataHorizontal: List<AlbumView>) {
        albums_rv?.isVisible = true
        tracklist_rv?.isVisible = true
        fullListAdapter.submitList(dataHorizontal)
    }

    override fun showLoader(isLoading: Boolean) {
        tracklist_fragment_loader.isVisible = isLoading
    }

    override fun showError(errorText: String) {
        tracklist_fragment_error.isVisible = true
        tracklist_fragment_error.text = errorText
    }
}