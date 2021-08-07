package test.dev.albumdisplayer.presentation.album

import test.dev.albumdisplayer.domain.entity.AlbumData

sealed class AlbumsViewState {
    object LOADER : AlbumsViewState()
    object ERROR : AlbumsViewState()
    object EMPTY : AlbumsViewState()
    data class SUCCESS(val data: Map<Int, List<AlbumData>>) : AlbumsViewState()
}
