package com.govi.s3bucketdemo.di.component

import android.app.Application
import com.govi.s3bucketdemo.S3BucketDemoApplication
import com.govi.s3bucketdemo.di.builder.ActivityBuilder
import com.govi.s3bucketdemo.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityBuilder::class])
interface AppComponent {
    fun inject(app: S3BucketDemoApplication)
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}