package test.dev.albumdisplayer.domain

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import test.dev.albumdisplayer.TestAlbum
import test.dev.albumdisplayer.data.remote.response.GetAlbumListResponse
import test.dev.albumdisplayer.data.repository.AlbumRepository
import test.dev.albumdisplayer.domain.entity.AlbumsEntity
import kotlin.test.assertEquals

class GetTestListUseCaseTest {
    private var albumRepository: AlbumRepository = mockk()
    private var classUnderTest = GetAlbumListUseCase(albumRepository)

    @Before
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `All data`() {
        runBlocking {
            //GIVEN
            val expectedResult = AlbumsEntity.SUCCESS(TestAlbum.ENTITY_SUCCESS)

            coEvery { albumRepository.getAlbumList() } returns RESPONSE_OK

            //WHEN
            val result = classUnderTest.invoke()

            //THEN
            assertEquals(expectedResult, result)
            coVerify(exactly = 1) {
                albumRepository.getAlbumList()
            }
        }
    }

    @Test
    fun `Data have a problem (null reference or empty string)`() {
        runBlocking {
            //GIVEN
            val expectedResult = AlbumsEntity.SUCCESS(TestAlbum.ENTITY_WITH_ERRORS)

            coEvery { albumRepository.getAlbumList() } returns RESPONSE_KO

            //WHEN
            val result = classUnderTest.invoke()

            //THEN
            assertEquals(expectedResult, result)
            coVerify(exactly = 1) {
                albumRepository.getAlbumList()
            }
        }
    }

    @Test
    fun `Empty data`() {
        runBlocking {
            //GIVEN
            val expectedResult = AlbumsEntity.EMPTY

            coEvery { albumRepository.getAlbumList() } returns emptyList()

            //WHEN
            val result = classUnderTest.invoke()

            //THEN
            assertEquals(expectedResult, result)
            coVerify(exactly = 1) {
                albumRepository.getAlbumList()
            }
        }
    }

    @Test
    fun `Null data`() {
        runBlocking {
            //GIVEN
            val expectedResult = AlbumsEntity.ERROR

            coEvery { albumRepository.getAlbumList() } returns null

            //WHEN
            val result = classUnderTest.invoke()

            //THEN
            assertEquals(expectedResult, result)
            coVerify(exactly = 1) {
                albumRepository.getAlbumList()
            }
        }
    }

    val RESPONSE_OK = Gson().fromJson<List<GetAlbumListResponse>>(javaClass.classLoader?.getResourceAsStream(TestAlbum.REMOTE_FILE_SUCCESS)
        ?.bufferedReader().use { it?.readText() }, object : TypeToken<List<GetAlbumListResponse>>() {}.type
    )

    val RESPONSE_KO = Gson().fromJson<List<GetAlbumListResponse>>(javaClass.classLoader?.getResourceAsStream(TestAlbum.REMOTE_FILE_WRONG)
        ?.bufferedReader().use { it?.readText() }, object : TypeToken<List<GetAlbumListResponse>>() {}.type
    )
}