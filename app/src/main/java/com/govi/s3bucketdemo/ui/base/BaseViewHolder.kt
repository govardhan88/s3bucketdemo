package com.govi.s3bucketdemo.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(position: Int)
}