package test.dev.albumdisplayer.presentation.album

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import test.dev.albumdisplayer.TestAlbum
import test.dev.albumdisplayer.domain.GetAlbumListUseCase
import test.dev.albumdisplayer.domain.entity.AlbumsEntity
import test.dev.albumdisplayer.presentation.Event

class AlbumsViewModelTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getAlbumListUseCase: GetAlbumListUseCase = mockk()
    private val classUnderTest: AlbumsViewModel by lazy { AlbumsViewModel(getAlbumListUseCase, dispatcher) }

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    private val albumListObserver: Observer<AlbumsViewState> = spyk()
    private val navigationObserver: Observer<Event<AlbumsNavigation>> = spyk()

    @Before
    fun setUp() {
        clearAllMocks()
    }

    @After
    fun finally() {
        classUnderTest.liveDataNavigation.removeObserver(navigationObserver)
        classUnderTest.liveDataAlbumList.removeObserver(albumListObserver)
    }

    @Test
    fun `init ok`() {
        runBlocking {
            //GIVEN
            coEvery { getAlbumListUseCase.invoke() } returns AlbumsEntity.SUCCESS(TestAlbum.ENTITY_SUCCESS)

            //WHEN
            classUnderTest.liveDataNavigation.observeForever(navigationObserver)
            classUnderTest.liveDataAlbumList.observeForever(albumListObserver)

            //THEN
            coVerifySequence {
                getAlbumListUseCase.invoke()
                navigationObserver.onChanged(Event(AlbumsNavigation.LIST))
                albumListObserver.onChanged(AlbumsViewState.SUCCESS(TestAlbum.VIEW_STATE_SUCCESS, TestAlbum.ENTITY_SUCCESS))
            }
        }
    }

    @Test
    fun `init ko`() {
        runBlocking {
            //GIVEN
            coEvery { getAlbumListUseCase.invoke() } returns AlbumsEntity.ERROR

            //WHEN
            classUnderTest.liveDataNavigation.observeForever(navigationObserver)
            classUnderTest.liveDataAlbumList.observeForever(albumListObserver)

            //THEN
            coVerifySequence {
                getAlbumListUseCase.invoke()
                navigationObserver.onChanged(Event(AlbumsNavigation.LIST))
                albumListObserver.onChanged(AlbumsViewState.ERROR)
            }
        }
    }

    @Test
    fun `init empty`() {
        runBlocking {
            //GIVEN
            coEvery { getAlbumListUseCase.invoke() } returns AlbumsEntity.EMPTY

            //WHEN
            classUnderTest.liveDataNavigation.observeForever(navigationObserver)
            classUnderTest.liveDataAlbumList.observeForever(albumListObserver)

            //THEN
            coVerifySequence {
                getAlbumListUseCase.invoke()
                navigationObserver.onChanged(Event(AlbumsNavigation.LIST))
                albumListObserver.onChanged(AlbumsViewState.EMPTY)
            }
        }
    }

    @Test
    fun `init throw exception`() {
        runBlocking {
            //GIVEN
            coEvery { getAlbumListUseCase.invoke() } throws Exception()

            //WHEN
            classUnderTest.liveDataNavigation.observeForever(navigationObserver)
            classUnderTest.liveDataAlbumList.observeForever(albumListObserver)

            //THEN
            coVerifySequence {
                getAlbumListUseCase.invoke()
                navigationObserver.onChanged(Event(AlbumsNavigation.LIST))
                albumListObserver.onChanged(AlbumsViewState.ERROR)
            }
        }
    }


}