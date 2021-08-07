package test.dev.albumdisplayer.domain

import test.dev.albumdisplayer.data.remote.response.GetAlbumListResponse
import test.dev.albumdisplayer.data.repository.AlbumRepository

class GetAlbumListUseCase(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke(): List<GetAlbumListResponse>? {
        return albumRepository.getAlbumList()
    }
}