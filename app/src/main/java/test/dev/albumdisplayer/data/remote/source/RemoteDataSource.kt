package test.dev.albumdisplayer.data.remote.source

import test.dev.albumdisplayer.data.remote.LBCService
import test.dev.albumdisplayer.data.remote.response.GetAlbumListResponse

class RemoteDataSource(private val service: LBCService) {
    suspend fun getAlbums(): List<GetAlbumListResponse>? {
        val response = service.getAlbumList()
        return when {
            response.isSuccessful -> response.body()
            else -> null
        }
    }
}
