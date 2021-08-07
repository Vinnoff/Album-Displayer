package test.dev.albumdisplayer.data.repository

import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import test.dev.albumdisplayer.data.remote.response.GetAlbumListResponse
import test.dev.albumdisplayer.data.remote.source.RemoteDataSource
import kotlin.test.assertEquals

class AlbumRepositoryImplTest {
    private val datasource: RemoteDataSource = mockk()
    private val classUnderTest = AlbumRepositoryImpl(datasource)

    @Before
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `track list returns a result`() {
        runBlocking {
            //GIVEN
            val response = listOf(
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
                GetAlbumListResponse(albumId = ArgumentMatchers.anyInt(), id = ArgumentMatchers.anyInt(), title = ArgumentMatchers.anyString(), url = ArgumentMatchers.anyString(), thumbnailUrl = ArgumentMatchers.anyString()),
            )
            coEvery { datasource.getAlbums() } returns response

            //WHEN
            val result = classUnderTest.getAlbumList()

            //THEN
            assertEquals(response, result)
            coVerify(exactly = 1) {
                datasource.getAlbums()
            }
            confirmVerified(datasource)
        }
    }

    @Test
    fun `track list returns null`() {
        runBlocking {
            //GIVEN
            val response = null
            coEvery { datasource.getAlbums() } returns response

            //WHEN
            val result = classUnderTest.getAlbumList()

            //THEN
            assertEquals(response, result)
            coVerify(exactly = 1) {
                datasource.getAlbums()
            }
            confirmVerified(datasource)
        }
    }
}