package test.dev.albumdisplayer.presentation.album.list

import test.dev.albumdisplayer.domain.entity.AlbumData

data class SelectedAlbumsViewState(
    val needToShowPrevious: Boolean,
    val needToShowNext: Boolean,
    val position: Int,
    val data: List<AlbumData>?
) {

}
