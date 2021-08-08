package test.dev.albumdisplayer.presentation.album.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.album_header_item.view.*
import kotlinx.android.synthetic.main.album_list_item.view.*
import test.dev.albumdisplayer.R
import test.dev.albumdisplayer.common.utils.load
import test.dev.albumdisplayer.domain.entity.AlbumData
import test.dev.albumdisplayer.presentation.album.list.AlbumView

class FullListAdapter : ListAdapter<AlbumView, FullListAdapter.ViewHolder>(ItemCallback()) {
    private val HEADER = 1
    private val CONTENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(if (viewType == CONTENT) R.layout.album_list_item else R.layout.album_header_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentList[position].let { data ->
            when (data) {
                is AlbumView.HEADER -> holder.bindHeader(data.albumId)
                is AlbumView.CONTENT -> holder.bindContent(data.album)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is AlbumView.HEADER -> HEADER
            is AlbumView.CONTENT -> CONTENT
        }
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindHeader(id: Int) {
            with(view) {
                album_header_id.text = "Album : $id"
            }
        }

        fun bindContent(album: AlbumData) {
            with(view) {
                album_list_item_thumbnail.load(album.thumbnailUrl)
                album_list_item_id.text = album.id.toString()
                album_list_item_title.text = album.title
            }
        }
    }
}

class ItemCallback : DiffUtil.ItemCallback<AlbumView>() {
    override fun areItemsTheSame(oldItem: AlbumView, newItem: AlbumView) = when (oldItem) {
        is AlbumView.CONTENT -> oldItem.album.id == (newItem as? AlbumView.CONTENT)?.album?.id
        is AlbumView.HEADER -> oldItem.albumId == (newItem as? AlbumView.HEADER)?.albumId
    }

    override fun areContentsTheSame(oldItem: AlbumView, newItem: AlbumView) = oldItem == newItem
}