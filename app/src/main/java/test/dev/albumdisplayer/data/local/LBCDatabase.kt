package test.dev.albumdisplayer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import test.dev.albumdisplayer.data.response.GetAlbumListDatabase

@Database(entities = [GetAlbumListDatabase::class], version = 1, exportSchema = false)
abstract class LBCDatabase : RoomDatabase() {
    abstract val lbcDao: LBCDao
}
