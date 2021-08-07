package test.dev.albumdisplayer.domain.entity

data class AlbumData(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String?,
    val thumbnailUrl: String?,
)