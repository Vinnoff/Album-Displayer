package test.dev.albumdisplayer.presentation

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class SpaceItemDecoration(@Px private val spacing: Int, private val includeEdge: Boolean = true) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        when (parent.layoutManager) {
            is GridLayoutManager -> outRect.setForGrid(parent, view)
            is StaggeredGridLayoutManager -> outRect.setForStaggeredGrid(parent, view)
            is LinearLayoutManager -> outRect.setForLinear(parent, view)
        }
    }

    private fun Rect.setForGrid(parent: RecyclerView, view: View) {
        val position = parent.getChildAdapterPosition(view)
        val spanCount = (parent.layoutManager as GridLayoutManager).spanCount
        val column = position % spanCount
        if (includeEdge) {
            left = spacing - column * spacing / spanCount
            right = (column + 1) * spacing / spanCount
            if (position < spanCount) top = spacing
            bottom = spacing
        } else {
            left = column * spacing / spanCount
            right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) top = spacing
        }
    }

    private fun Rect.setForStaggeredGrid(parent: RecyclerView, view: View) {
        val position = parent.getChildAdapterPosition(view)
        val spanCount = (parent.layoutManager as StaggeredGridLayoutManager).spanCount
        val column = position % spanCount
        if (includeEdge) {
            left = spacing - column * spacing / spanCount
            right = (column + 1) * spacing / spanCount
            if (position < spanCount) top = spacing
            bottom = spacing
        } else {
            left = column * spacing / spanCount
            right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) top = spacing
        }
    }

    private fun Rect.setForLinear(parent: RecyclerView, view: View) {
        val position = parent.getChildAdapterPosition(view)
        when ((parent.layoutManager as LinearLayoutManager).orientation) {
            RecyclerView.HORIZONTAL -> {
                top = if (includeEdge) spacing else 0
                bottom = if (includeEdge) spacing else 0
                left = if (position == 0) spacing else 0
                right = spacing
            }
            RecyclerView.VERTICAL -> {
                left = if (includeEdge) spacing else 0
                right = if (includeEdge) spacing else 0
                top = if (position == 0) spacing else 0
                bottom = spacing
            }
        }
    }
}