package test.dev.albumdisplayer.presentation.album

import test.dev.albumdisplayer.domain.entity.AlbumData
import test.dev.albumdisplayer.domain.entity.AlbumsEntity

sealed class AlbumsViewState {
    object LOADER : AlbumsViewState()
    object ERROR : AlbumsViewState()
    object EMPTY : AlbumsViewState()
    data class SUCCESS(val data: List<AlbumView>) : AlbumsViewState()
}

fun AlbumsEntity.toViewState(): AlbumsViewState {
    return when (this) {
        is AlbumsEntity.EMPTY -> AlbumsViewState.EMPTY
        is AlbumsEntity.ERROR -> AlbumsViewState.ERROR
        is AlbumsEntity.SUCCESS -> data.toViewState()
    }
}

private fun Map<Int, List<AlbumData>>.toViewState(): AlbumsViewState.SUCCESS {
    val entriesIndex = entries.map { it.key to it.value.size }
    var indexOfHeaderToInsert = 0

    val list = arrayListOf<AlbumView>()
    values.flatten().mapTo(list) { data -> AlbumView.CONTENT(data) }
    entriesIndex.forEach {
        list.add(indexOfHeaderToInsert, AlbumView.HEADER(it.first))
        indexOfHeaderToInsert += it.second + 1 //the accumulator calculate the next position which is its position + the content size + 1 for the new header
    }
    return AlbumsViewState.SUCCESS(list)
}