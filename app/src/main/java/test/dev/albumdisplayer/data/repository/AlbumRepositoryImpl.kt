package test.dev.albumdisplayer.data.repository

import test.dev.albumdisplayer.common.utils.isInternetAvailable
import test.dev.albumdisplayer.data.local.source.LocalDataSource
import test.dev.albumdisplayer.data.remote.source.RemoteDataSource
import test.dev.albumdisplayer.data.response.GetAlbumListResponse

class AlbumRepositoryImpl(private val localDataSource: LocalDataSource, private val remoteDataSource: RemoteDataSource) : AlbumRepository {
    override suspend fun getAlbumList(): List<GetAlbumListResponse>? {
        return if (isInternetAvailable())
            remoteDataSource.getAlbums().putInCache() ?: localDataSource.getAlbums()
        else localDataSource.getAlbums()
    }

    private suspend fun List<GetAlbumListResponse>?.putInCache(): List<GetAlbumListResponse>? {
        if (isNullOrEmpty()) return null
        localDataSource.putAlbumsInCache(this)
        return this
    }
}
