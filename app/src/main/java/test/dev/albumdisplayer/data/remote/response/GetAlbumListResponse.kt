package test.dev.albumdisplayer.data.remote.response

data class GetAlbumListResponse(
    val albumId: Int?,
    val id: Int?,
    val title: String?,
    val url: String?,
    val thumbnailUrl: String?
)