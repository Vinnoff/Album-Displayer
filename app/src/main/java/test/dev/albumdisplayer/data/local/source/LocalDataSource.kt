package test.dev.albumdisplayer.data.local.source

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import test.dev.albumdisplayer.data.local.LBCDao
import test.dev.albumdisplayer.data.response.GetAlbumListDatabase
import test.dev.albumdisplayer.data.response.GetAlbumListResponse

class LocalDataSource(private val dao: LBCDao) {
    suspend fun getAlbums(): List<GetAlbumListResponse>? {
        return withContext(Dispatchers.Default) {
            dao.getAlbumList().map { it.toResponse() }.ifEmpty { null }
        }
    }

    suspend fun putAlbumsInCache(toCache: List<GetAlbumListResponse>) {
        return withContext(Dispatchers.Default) {
            dao.insertAlbumList(toCache.map { it.toModel() })
        }
    }
}

private fun GetAlbumListResponse.toModel(): GetAlbumListDatabase {
    return GetAlbumListDatabase(
        albumId = albumId,
        id = id,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl,
    )
}

private fun GetAlbumListDatabase.toResponse(): GetAlbumListResponse {
    return GetAlbumListResponse(
        albumId = albumId,
        id = id,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl,
    )
}
