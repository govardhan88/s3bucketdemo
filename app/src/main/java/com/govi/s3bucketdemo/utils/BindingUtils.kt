package com.govi.s3bucketdemo.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.govi.s3bucketdemo.ui.base.BaseRecyclerViewAdapter

object BindingUtils {
    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    @BindingAdapter("adapter")
    fun <T> setRecyclerViewData(recyclerView: RecyclerView, items: List<T>?) {
        items?.let {
            (recyclerView.adapter as? BaseRecyclerViewAdapter<T>)?.apply {
                clearItems()
                addItems(items)
            }
        }
    }

}