package test.dev.albumdisplayer.data.repository

import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import test.dev.albumdisplayer.common.utils.isInternetAvailable
import test.dev.albumdisplayer.data.local.source.LocalDataSource
import test.dev.albumdisplayer.data.remote.source.RemoteDataSource
import test.dev.albumdisplayer.data.response.GetAlbumListResponse
import kotlin.test.assertEquals

class AlbumRepositoryImplTest {
    private val remoteDataSource: RemoteDataSource = mockk()
    private val localDataSource: LocalDataSource = mockk()
    private val classUnderTest = AlbumRepositoryImpl(localDataSource, remoteDataSource)

    @Before
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `track list returns a good result from remote`() {
        runBlocking {
            //GIVEN
            val response = listOf(
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
            )
            coEvery { remoteDataSource.getAlbums() } returns response
            coEvery { localDataSource.putAlbumsInCache(response) } returns Unit

            //WHEN
            val result = classUnderTest.getAlbumList()

            //THEN
            assertEquals(response, result)
            coVerifySequence {
                remoteDataSource.getAlbums()
                localDataSource.putAlbumsInCache(response)
            }
            confirmVerified(localDataSource, remoteDataSource)
        }
    }

    @Test
    fun `track list returns a bad result from remote`() {
        runBlocking {
            //GIVEN
            val response = listOf(
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
            )
            coEvery { remoteDataSource.getAlbums() } returns null
            coEvery { localDataSource.getAlbums() } returns response
            coEvery { localDataSource.putAlbumsInCache(response) } returns Unit

            //WHEN
            val result = classUnderTest.getAlbumList()

            //THEN
            assertEquals(response, result)
            coVerifySequence {
                remoteDataSource.getAlbums()
                localDataSource.getAlbums()
            }
            confirmVerified(localDataSource, remoteDataSource)
        }
    }

    @Test
    fun `track list returns null and no local data found`() {
        runBlocking {
            //GIVEN
            val response = null
            coEvery { remoteDataSource.getAlbums() } returns response
            coEvery { localDataSource.getAlbums() } returns response

            //WHEN
            val result = classUnderTest.getAlbumList()

            //THEN
            assertEquals(response, result)
            coVerifySequence {
                remoteDataSource.getAlbums()
                localDataSource.getAlbums()
            }
            confirmVerified(remoteDataSource)
        }
    }

    @Test
    fun `No Internet and database filled`() {
        runBlocking {
            //GIVEN
            mockkStatic("test.dev.albumdisplayer.common.utils.StandardsKt") // This is what I was missing
            val response = listOf(
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
            )
            every { isInternetAvailable() } returns false
            coEvery { localDataSource.getAlbums() } returns response

            //WHEN
            val result = classUnderTest.getAlbumList()

            //THEN
            assertEquals(response, result)
            coVerifySequence {
                localDataSource.getAlbums()
            }
            confirmVerified(localDataSource, remoteDataSource)
        }
    }

    @Test
    fun `No Internet and database empty`() {
        runBlocking {
            //GIVEN
            mockkStatic("test.dev.albumdisplayer.common.utils.StandardsKt") // This is what I was missing
            val response = null
            every { isInternetAvailable() } returns false
            coEvery { localDataSource.getAlbums() } returns response

            //WHEN
            val result = classUnderTest.getAlbumList()

            //THEN
            assertEquals(response, result)
            coVerifySequence {
                localDataSource.getAlbums()
            }
            confirmVerified(localDataSource, remoteDataSource)
        }
    }
}