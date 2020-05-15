package com.govi.s3bucketdemo.ui.base

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<N>(
    val application: Application
) : ViewModel(), CoroutineScope {
    val isLoading = ObservableBoolean()

    private val job: Job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job // By default child coroutines will run on the main thread.

    override fun onCleared() {
        super.onCleared()
        job.cancel() // Parent Job cancels all child coroutines.
    }

    fun setIsLoading(b: Boolean) {
        isLoading.set(b)
    }

}