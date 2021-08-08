package test.dev.albumdisplayer.data.local.source

import test.dev.albumdisplayer.data.local.LBCDao
import test.dev.albumdisplayer.data.response.GetAlbumListDatabase
import test.dev.albumdisplayer.data.response.GetAlbumListResponse

class LocalDataSource(private val dao: LBCDao) {
    fun getAlbums(): List<GetAlbumListResponse>? {
        return dao.getAlbumList().map { it.toResponse() }.ifEmpty { null }
    }

    fun putAlbumsInCache(toCache: List<GetAlbumListResponse>) {
        dao.insertAlbumList(toCache.map { it.toModel() })
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
