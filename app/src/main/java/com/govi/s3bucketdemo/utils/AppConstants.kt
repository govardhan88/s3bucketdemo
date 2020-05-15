package com.govi.s3bucketdemo.utils

import com.govi.s3bucketdemo.BuildConfig

object AppConstants {
    const val DB_NAME = BuildConfig.APPLICATION_ID + ".db"
    const val PREF_NAME = BuildConfig.APPLICATION_ID + "_pref"
    const val FILE = "file"
    const val VIEW_TYPE_EMPTY = 0
    const val VIEW_TYPE_NORMAL = 1
}