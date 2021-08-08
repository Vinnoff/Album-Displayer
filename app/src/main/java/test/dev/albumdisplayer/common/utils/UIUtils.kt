package test.dev.albumdisplayer.common.utils

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import test.dev.albumdisplayer.R

fun ImageView.load(imageUrl: String?, @DrawableRes placeHolderRes: Int = R.drawable.shape_album_placeholder) = Glide
    .with(context)
    .load(imageUrl)
    .placeholder(placeHolderRes)
    .transition(DrawableTransitionOptions.withCrossFade())
    .into(this)

fun Int.toPx(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}