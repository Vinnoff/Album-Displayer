package test.dev.albumdisplayer.data.repository

import test.dev.albumdisplayer.data.remote.response.GetAlbumListResponse
import test.dev.albumdisplayer.data.remote.source.RemoteDataSource

class AlbumRepositoryImpl(private val remoteDataSource: RemoteDataSource) : AlbumRepository {
    override suspend fun getAlbumList(): List<GetAlbumListResponse>? {
        return remoteDataSource.getAlbums()
    }
}