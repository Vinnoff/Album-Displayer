package test.dev.albumdisplayer.domain.entity

sealed class AlbumsEntity {
    data class SUCCESS(val data: Map<Int, List<AlbumData>>) : AlbumsEntity()
    object ERROR : AlbumsEntity()
    object EMPTY : AlbumsEntity()
}
