package test.dev.albumdisplayer.data.repository

import test.dev.albumdisplayer.data.remote.response.GetAlbumListResponse

interface AlbumRepository {
    suspend fun getAlbumList(): List<GetAlbumListResponse>?
}
