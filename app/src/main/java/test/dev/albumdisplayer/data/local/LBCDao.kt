package test.dev.albumdisplayer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import test.dev.albumdisplayer.data.response.GetAlbumListDatabase

@Dao
interface LBCDao {

    @Query(QUERY_ALBUM_LIST)
    fun getAlbumList(): List<GetAlbumListDatabase>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbumList(cachedAlbums: List<GetAlbumListDatabase>)

    companion object {
        const val ALBUMS_TABLE_NAME = "ALBUMS"
        private const val QUERY_ALBUM_LIST = "SELECT * FROM $ALBUMS_TABLE_NAME"
        private const val DELETE_ALL_ALBUMS = "DELETE FROM $ALBUMS_TABLE_NAME"
    }
}