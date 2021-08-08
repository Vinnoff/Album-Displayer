package test.dev.albumdisplayer.presentation.album.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.album_header_item.view.*
import test.dev.albumdisplayer.R

class HeaderAdapter : ListAdapter<Int, HeaderAdapter.ViewHolder>(ItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.album_header_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentList[position].let { data ->
            holder.bind(data)
        }
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(id: Int) {
            with(view) {
                album_header_id.text = "Album\n$id"
            }
        }
    }

    class ItemCallback : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
    }
}