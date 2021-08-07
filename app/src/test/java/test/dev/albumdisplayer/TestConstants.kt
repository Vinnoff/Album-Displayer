package test.dev.albumdisplayer

import test.dev.albumdisplayer.domain.entity.AlbumData
import test.dev.albumdisplayer.presentation.album.AlbumView

class TestAlbum {

    companion object {
        const val REMOTE_FILE_SUCCESS = "albums_ok.json"
        const val REMOTE_FILE_WRONG = "albums_wrong.json"

        val ENTITY_SUCCESS = mapOf(
            1 to listOf(
                AlbumData(id = 11, albumId = 1, title = "titre 1", url = "url", thumbnailUrl = "url_thumbnail"),
                AlbumData(id = 12, albumId = 1, title = "titre 2", url = "url", thumbnailUrl = "url_thumbnail"),
                AlbumData(id = 13, albumId = 1, title = "titre 3", url = "url", thumbnailUrl = "url_thumbnail"),
            ),
            2 to listOf(
                AlbumData(id = 21, albumId = 2, title = "titre 1", url = "url_thumbnail", thumbnailUrl = "url_thumbnail"),
                AlbumData(id = 22, albumId = 2, title = "titre 2", url = "url", thumbnailUrl = "url"),
            ),
            3 to listOf(
                AlbumData(id = 31, albumId = 3, title = "titre 1", url = "url", thumbnailUrl = "url_thumbnail"),
            ),
        )

        val ENTITY_WITH_ERRORS = mapOf(
            2 to listOf(
                AlbumData(id = 21, albumId = 2, title = "titre 1", url = null, thumbnailUrl = null),
            ),
            3 to listOf(
                AlbumData(id = 31, albumId = 3, title = "titre 1", url = null, thumbnailUrl = null),
            ),
        )

        val VIEW_STATE_SUCCESS = listOf(
            AlbumView.HEADER(1),
            AlbumView.CONTENT(AlbumData(id = 11, albumId = 1, title = "titre 1", url = "url", thumbnailUrl = "url_thumbnail")),
            AlbumView.CONTENT(AlbumData(id = 12, albumId = 1, title = "titre 2", url = "url", thumbnailUrl = "url_thumbnail")),
            AlbumView.CONTENT(AlbumData(id = 13, albumId = 1, title = "titre 3", url = "url", thumbnailUrl = "url_thumbnail")),
            AlbumView.HEADER(2),
            AlbumView.CONTENT(AlbumData(id = 21, albumId = 2, title = "titre 1", url = "url_thumbnail", thumbnailUrl = "url_thumbnail")),
            AlbumView.CONTENT(AlbumData(id = 22, albumId = 2, title = "titre 2", url = "url", thumbnailUrl = "url")),
            AlbumView.HEADER(3),
            AlbumView.CONTENT(AlbumData(id = 31, albumId = 3, title = "titre 1", url = "url", thumbnailUrl = "url_thumbnail")),
        )
    }
}