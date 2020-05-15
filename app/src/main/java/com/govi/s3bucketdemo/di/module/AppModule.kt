package com.govi.s3bucketdemo.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import com.govi.s3bucketdemo.BuildConfig
import com.govi.s3bucketdemo.di.PreferenceInfo
import com.govi.s3bucketdemo.utils.AppConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideBasicAWSCredentials(): BasicAWSCredentials =
        BasicAWSCredentials(BuildConfig.ACCESS_KEY, BuildConfig.SECRET_KEY)

    @Provides
    @Singleton
    fun provideS3Client(credentials: BasicAWSCredentials) = AmazonS3Client(credentials)

    @Provides
    @Singleton
    fun provideTransferUtility(s3Client: AmazonS3Client, application: Application) =
        TransferUtility.builder()
        .context(application)
        .awsConfiguration(AWSMobileClient.getInstance().configuration)
        .s3Client(s3Client)
        .build()

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return AppConstants.PREF_NAME
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @PreferenceInfo prefName: String,
        context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }
}