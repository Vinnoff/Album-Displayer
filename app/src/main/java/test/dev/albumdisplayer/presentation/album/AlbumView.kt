package test.dev.albumdisplayer.presentation.album

import test.dev.albumdisplayer.domain.entity.AlbumData

sealed class AlbumView {
    data class HEADER(val albumId: Int) : AlbumView()
    data class CONTENT(val album: AlbumData) : AlbumView()
}
