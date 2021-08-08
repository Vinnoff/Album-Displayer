package test.dev.albumdisplayer.presentation.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import test.dev.albumdisplayer.common.utils.CustomCoroutineExceptionHandler
import test.dev.albumdisplayer.domain.GetAlbumListUseCase
import test.dev.albumdisplayer.domain.entity.AlbumData
import test.dev.albumdisplayer.presentation.BaseViewModel
import test.dev.albumdisplayer.presentation.Event
import test.dev.albumdisplayer.presentation.toEvent

class AlbumsViewModel(
    private val getAlbumListUseCase: GetAlbumListUseCase,
    dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher) {
    private val _liveDataNavigation: MutableLiveData<Event<AlbumsNavigation>> = MutableLiveData()
    val liveDataNavigation: LiveData<Event<AlbumsNavigation>> get() = _liveDataNavigation

    private val _liveDataAlbumList: MutableLiveData<AlbumsViewState> = MutableLiveData()
    val liveDataAlbumList: LiveData<AlbumsViewState> get() = _liveDataAlbumList
    private val albumList get() = (liveDataAlbumList.value as? AlbumsViewState.SUCCESS)?.dataVertical

    private val _liveDataTrackList: MutableLiveData<List<AlbumData>> = MutableLiveData()
    val liveDataTrackList: LiveData<List<AlbumData>> get() = _liveDataTrackList

    init {
        _liveDataNavigation.value = AlbumsNavigation.LIST.toEvent()
        _liveDataAlbumList.value = AlbumsViewState.LOADER
        launch(CustomCoroutineExceptionHandler { _liveDataAlbumList.value = AlbumsViewState.ERROR }) {
            _liveDataAlbumList.value = getAlbumListUseCase.invoke().toViewState()
        }
    }

    fun onAlbumSelected(albumPosition: Int) {
        _liveDataTrackList.value = albumList?.values?.toTypedArray()?.get(albumPosition)
    }
}