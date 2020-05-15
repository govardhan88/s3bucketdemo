package com.govi.s3bucketdemo.ui.main

import android.app.Application
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.S3ObjectSummary
import com.govi.s3bucketdemo.ui.base.BaseViewModel
import com.govi.s3bucketdemo.utils.FileOpen
import com.govi.s3bucketdemo.utils.FileOpen.openFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

class MainViewModel(
    application: Application,
    private val s3Client: AmazonS3Client,
    private val transferUtility: TransferUtility
) : BaseViewModel<Any>(application) {
    private val objectSummariesMutable = MutableLiveData<MutableList<S3ObjectSummary>>()
    val objectSummariesLiveData: LiveData<MutableList<S3ObjectSummary>> =
        objectSummariesMutable

    init {
        objectSummariesMutable.value = mutableListOf()
       loadData()
    }

    private fun loadData() {
        CoroutineScope(IO).launch {
            val objectSummaries = getBucketList()
            withContext(Main) {
                objectSummariesMutable.value = objectSummaries
            }
        }
    }

    private fun getBucketList(): MutableList<S3ObjectSummary>? {
        s3Client.listBuckets().forEach { bucket ->
            println(message = "${bucket.name} --> ${bucket.owner} --> ${bucket.creationDate}")
            return s3Client.listObjects(bucket.name).objectSummaries
        }
        return mutableListOf()
    }

    fun downloadFile(s3ObjectSummary: S3ObjectSummary) {
        val localFile = getLocalFile(s3ObjectSummary.key)

        if(localFile.exists()){
            openFile(application,localFile)
        }else{
            CoroutineScope(IO).launch {

                val downloadObserver =
                    transferUtility.download(s3ObjectSummary.bucketName, s3ObjectSummary.key, localFile)

                downloadObserver.setTransferListener(object : TransferListener {

                    override fun onStateChanged(id: Int, state: TransferState) {
                        if (TransferState.COMPLETED == state)
                            openFile(context = application, url = localFile)
                    }

                    override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                        val percentDonef = bytesCurrent.toFloat() / bytesTotal.toFloat() * 100
                        val percentDone = percentDonef.toInt()

                        println("ID:$id|bytesCurrent: $bytesCurrent|bytesTotal: $bytesTotal|$percentDone%")
                    }

                    override fun onError(id: Int, ex: Exception) {
                        ex.printStackTrace()
                    }
                })
            }
        }
    }

    fun onRetryClick(){
        loadData()
    }

    private fun getLocalFile(fileKey: String): File {
        val path = application.getExternalFilesDir("S3Bucket")
        val file = File(path, fileKey)
        try {
            // Make sure the Pictures directory exists.
            path?.mkdirs()
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }
}