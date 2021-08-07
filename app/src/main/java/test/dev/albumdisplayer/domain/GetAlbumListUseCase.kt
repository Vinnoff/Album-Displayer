package test.dev.albumdisplayer.domain

import test.dev.albumdisplayer.data.remote.response.GetAlbumListResponse
import test.dev.albumdisplayer.data.repository.AlbumRepository
import test.dev.albumdisplayer.domain.entity.AlbumData
import test.dev.albumdisplayer.domain.entity.AlbumsEntity

class GetAlbumListUseCase(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke(): AlbumsEntity {
        val response = albumRepository.getAlbumList()?.mapNotNull { it.toEntity() }?.groupBy { it.albumId }
        return when {
            response == null -> AlbumsEntity.ERROR
            response.isEmpty() -> AlbumsEntity.EMPTY
            else -> AlbumsEntity.SUCCESS(response)
        }
    }

    private fun GetAlbumListResponse.toEntity(): AlbumData? {
        return if (id != null && albumId != null && title != null && url != null) AlbumData(
            id = id,
            albumId = albumId,
            title = title,
            url = url,
            thumbnailUrl = thumbnailUrl ?: url,
        ) else null
    }
}
