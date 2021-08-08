package test.dev.albumdisplayer.data.local.source

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import test.dev.albumdisplayer.TestAlbum
import test.dev.albumdisplayer.data.local.LBCDao
import kotlin.test.assertEquals

class LocalDataSourceTest {
    private val lbcDao: LBCDao = mockk()
    private val classUnderTest = LocalDataSource(lbcDao)

    @Before
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `database filled`() {
        runBlocking {
            // GIVEN
            val response = TestAlbum.GENERIC_RESPONSE
            val database = TestAlbum.GENERIC_DATABASE

            coEvery { lbcDao.getAlbumList() } returns database

            // WHEN
            val result = classUnderTest.getAlbums()

            //THEN
            assertEquals(response, result)
            coVerifySequence {
                lbcDao.getAlbumList()
            }
        }
    }

    @Test
    fun `database empty`() {
        runBlocking {
            // GIVEN
            coEvery { lbcDao.getAlbumList() } returns emptyList()

            // WHEN
            val result = classUnderTest.getAlbums()

            //THEN
            assertEquals(null, result)
            coVerifySequence {
                lbcDao.getAlbumList()
            }
        }
    }

    @Test
    fun `put data in database`() {
        runBlocking {
            // GIVEN
            val response = TestAlbum.GENERIC_RESPONSE
            val database = TestAlbum.GENERIC_DATABASE

            coEvery { lbcDao.insertAlbumList(database) } returns Unit

            // WHEN
            classUnderTest.putAlbumsInCache(response)

            //THEN
            coVerifySequence {
                lbcDao.insertAlbumList(database)
            }
        }
    }
}