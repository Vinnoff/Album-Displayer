package test.dev.albumdisplayer.presentation.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import test.dev.albumdisplayer.domain.GetAlbumListUseCase
import test.dev.albumdisplayer.presentation.BaseViewModel

class AlbumsViewModel(
    private val getAlbumListUseCase: GetAlbumListUseCase,
    dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher) {
    private val _liveDataAlbumList: MutableLiveData<AlbumsViewState> = MutableLiveData()
    val liveDataAlbumList: LiveData<AlbumsViewState> get() = _liveDataAlbumList

    init {
        launch {
            _liveDataAlbumList.value = getAlbumListUseCase.invoke().toViewState()
        }
    }
}