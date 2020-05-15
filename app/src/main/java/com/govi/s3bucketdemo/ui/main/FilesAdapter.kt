package com.govi.s3bucketdemo.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.amazonaws.services.s3.model.S3ObjectSummary
import com.govi.s3bucketdemo.databinding.ItemEmptyViewBinding
import com.govi.s3bucketdemo.databinding.ItemFileViewBinding
import com.govi.s3bucketdemo.ui.base.BaseRecyclerViewAdapter
import com.govi.s3bucketdemo.ui.base.BaseViewHolder
import com.govi.s3bucketdemo.utils.AppConstants.VIEW_TYPE_EMPTY
import com.govi.s3bucketdemo.utils.AppConstants.VIEW_TYPE_NORMAL

class FilesAdapter(private val viewModel: MainViewModel,items: MutableList<S3ObjectSummary> ) :
    BaseRecyclerViewAdapter<S3ObjectSummary>(items) {

    override fun getItemCount(): Int {
        return if (items.size > 0) items.size else 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (items.isNotEmpty()) VIEW_TYPE_NORMAL else VIEW_TYPE_EMPTY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                FileItemViewHolder(
                    ItemFileViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
            else -> {
                EmptyViewHolder(
                    ItemEmptyViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
        }
    }

    inner class FileItemViewHolder(private val mBinding: ItemFileViewBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
            mBinding.s3Objectsummary = items[position]
            mBinding.viewModel = viewModel
            mBinding.executePendingBindings()
        }

    }

    inner class EmptyViewHolder(private val mBinding: ItemEmptyViewBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
            mBinding.viewModel = viewModel
            mBinding.executePendingBindings()
        }
    }

}