package test.dev.albumdisplayer.presentation.album.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.album_list_item.view.*
import test.dev.albumdisplayer.R
import test.dev.albumdisplayer.common.utils.load
import test.dev.albumdisplayer.domain.entity.AlbumData

class TrackListAdapter : ListAdapter<AlbumData, TrackListAdapter.ViewHolder>(ItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.album_list_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentList[position].let { data ->
            holder.bind(data)
        }
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(album: AlbumData) {
            with(view) {
                album_list_item_thumbnail.load(album.thumbnailUrl)
                album_list_item_id.text = album.id.toString()
                album_list_item_title.text = album.title
            }
        }
    }

    class ItemCallback : DiffUtil.ItemCallback<AlbumData>() {
        override fun areItemsTheSame(oldItem: AlbumData, newItem: AlbumData) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: AlbumData, newItem: AlbumData) = oldItem == newItem
    }
}
