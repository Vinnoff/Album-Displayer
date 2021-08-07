package test.dev.albumdisplayer.data.remote.source

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import retrofit2.Response
import test.dev.albumdisplayer.data.remote.LBCService
import test.dev.albumdisplayer.data.remote.response.GetAlbumListResponse
import kotlin.test.assertEquals

class RemoteDataSourceTest {
    private val lbcService: LBCService = mockk()
    private val classUnderTest = RemoteDataSource(lbcService)

    @Before
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `getAlbumList (success 200)`() {
        runBlocking {
            // GIVEN
            val response = listOf(
                GetAlbumListResponse(albumId = anyInt(), id = anyInt(), title = anyString(), url = anyString(), thumbnailUrl = anyString()),
                GetAlbumListResponse(albumId = anyInt(), id = anyInt(), title = anyString(), url = anyString(), thumbnailUrl = anyString()),
                GetAlbumListResponse(albumId = anyInt(), id = anyInt(), title = anyString(), url = anyString(), thumbnailUrl = anyString()),
                GetAlbumListResponse(albumId = anyInt(), id = anyInt(), title = anyString(), url = anyString(), thumbnailUrl = anyString()),
                GetAlbumListResponse(albumId = anyInt(), id = anyInt(), title = anyString(), url = anyString(), thumbnailUrl = anyString()),
            )

            coEvery { lbcService.getAlbumList() } returns Response.success(response)

            // WHEN
            val result = classUnderTest.getAlbums()

            //THEN
            assertEquals(response, result)
            coVerify(exactly = 1) {
                lbcService.getAlbumList()
            }
        }
    }

    @Test
    fun `getAlbumList (error 500)`() {
        runBlocking {
            // GIVEN
            coEvery { lbcService.getAlbumList() } returns Response.error(500, ResponseBody.create(MediaType.parse("application/json"), "{}"))

            // WHEN
            val result = classUnderTest.getAlbums()

            //THEN
            assertEquals(null, result)
            coVerify(exactly = 1) {
                lbcService.getAlbumList()
            }
        }
    }
}