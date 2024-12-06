package com.daniel.helloworld.di.module

import android.content.Context
import com.crocodic.core.data.CoreSession
import com.crocodic.core.helper.NetworkHelper
import com.daniel.helloworld.mytest.mahasiswa.api.ApiService
import com.daniel.helloworld.mytest.mahasiswa.data.AppDatabase
import com.daniel.helloworld.pertemuan12.database.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideCoreSession(@ApplicationContext context: Context) = CoreSession(context)


    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): MyDatabase =
        MyDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideFriendDao(database: MyDatabase) = database.friendDao()

    @Singleton
    @Provides
    fun providesMhsDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideMhsDao(database: AppDatabase) = database.mahasiswaDao()

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return NetworkHelper.provideApiService(
            baseUrl = "https://dummyjson.com/",
            okHttpClient = NetworkHelper.provideOkHttpClient(),
            converterFactory = listOf(GsonConverterFactory.create())
        )
    }
}