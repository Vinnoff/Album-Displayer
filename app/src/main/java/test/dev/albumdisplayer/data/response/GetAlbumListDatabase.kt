package test.dev.albumdisplayer.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import test.dev.albumdisplayer.data.local.LBCDao.Companion.ALBUMS_TABLE_NAME

@Entity(tableName = ALBUMS_TABLE_NAME)
data class GetAlbumListDatabase(
    @PrimaryKey
    val id: Int?,
    val albumId: Int?,
    val title: String?,
    val url: String?,
    val thumbnailUrl: String?,
)