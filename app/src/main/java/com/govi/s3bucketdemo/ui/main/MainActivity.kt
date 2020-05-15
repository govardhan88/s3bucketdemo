package com.govi.s3bucketdemo.ui.main

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.govi.s3bucketdemo.BR
import com.govi.s3bucketdemo.R
import com.govi.s3bucketdemo.databinding.ActivityMainBinding
import com.govi.s3bucketdemo.di.ViewModelProviderFactory
import com.govi.s3bucketdemo.ui.base.BaseActivity
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(),
    HasAndroidInjector {
    @Inject
    lateinit var factory: ViewModelProviderFactory

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_main

    private val filesAdapter by lazy {
        FilesAdapter(viewModel, mutableListOf())
    }

    override val viewModel: MainViewModel
        get() = ViewModelProvider(this, factory).get(MainViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyStoragePermissions(this)
        viewModel.objectSummariesLiveData.observe(this, Observer {
            filesAdapter.addItems(it)
        })
        setUpRecyclerView()
    }

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission =
            ActivityCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    private fun setUpRecyclerView() {
        viewDataBinding.filesRecycler.layoutManager = LinearLayoutManager(this)
        viewDataBinding.filesRecycler.itemAnimator = DefaultItemAnimator()
        viewDataBinding.filesRecycler.adapter = filesAdapter
    }

}