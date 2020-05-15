package com.govi.s3bucketdemo.di.builder

import com.govi.s3bucketdemo.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [])
    abstract fun bindMainActivity(): MainActivity
}