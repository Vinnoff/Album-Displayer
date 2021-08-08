package test.dev.albumdisplayer.presentation.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import test.dev.albumdisplayer.common.utils.CustomCoroutineExceptionHandler
import test.dev.albumdisplayer.domain.GetAlbumListUseCase
import test.dev.albumdisplayer.presentation.BaseViewModel
import test.dev.albumdisplayer.presentation.Event
import test.dev.albumdisplayer.presentation.album.list.SelectedAlbumsViewState
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

    private val _liveDataTrackList: MutableLiveData<SelectedAlbumsViewState> = MutableLiveData()
    val liveDataTrackList: LiveData<SelectedAlbumsViewState> get() = _liveDataTrackList

    init {
        _liveDataNavigation.postValue(AlbumsNavigation.LIST.toEvent())
        _liveDataAlbumList.postValue(AlbumsViewState.LOADER)
        launch(CustomCoroutineExceptionHandler { _liveDataAlbumList.postValue(AlbumsViewState.ERROR) }) {
            _liveDataAlbumList.postValue(getAlbumListUseCase.invoke().toViewState())
        }
    }

    fun onAlbumSelected(albumPosition: Int) {
        _liveDataTrackList.postValue(
            SelectedAlbumsViewState(
                needToShowPrevious = albumPosition != 0,
                needToShowNext = albumPosition != albumList?.size?.minus(1),
                position = albumPosition,
                data = albumList?.values?.toTypedArray()?.get(albumPosition)
            )
        )
    }
}