package com.daniel.helloworld.di.module

import android.content.Context
import com.crocodic.core.data.CoreSession
import com.crocodic.core.helper.NetworkHelper
import com.daniel.helloworld.mytest.mahasiswa.api.ApiAuthService
import com.daniel.helloworld.mytest.mahasiswa.api.ApiService
import com.daniel.helloworld.mytest.mahasiswa.data.AppDatabase
import com.daniel.helloworld.pertemuan12.api.ApiServiceProduct
import com.daniel.helloworld.pertemuan12.database.MyDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
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
    fun provideGson() = Gson()

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
    fun provideUserDao(database: AppDatabase) = database.userDao()

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return NetworkHelper.provideApiService(
            baseUrl = "https://dummyjson.com/",
            okHttpClient = NetworkHelper.provideOkHttpClient(),
            converterFactory = listOf(GsonConverterFactory.create())
        )
    }

    @Singleton
    @Provides
    fun provideApiServiceProduct(): ApiServiceProduct {
        return NetworkHelper.provideApiService(
            baseUrl = "https://dummyjson.com/",
            okHttpClient = NetworkHelper.provideOkHttpClient(),
            converterFactory = listOf(GsonConverterFactory.create())
        )
    }

    @Singleton
    @Provides
    fun providesOkHttp(session: CoreSession): OkHttpClient {

        return NetworkHelper.provideOkHttpClient().newBuilder().apply {
            addInterceptor {
                val original = it.request()
                val requestBuilder = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .method(original.method, original.body)

                val token = session.getString(CoreSession.PREF_UID)
                if (token.isNotEmpty()) {
                    requestBuilder.header("Authorization", "Bearer $token")
                }

                val request = requestBuilder.build()
                it.proceed(request)
            }
        }.build()
    }

    @Singleton
    @Provides
    fun provideApiAuthService(okHttpClient: OkHttpClient): ApiAuthService {
        return NetworkHelper.provideApiService(
            baseUrl = "https://kelas-industri.crocodic.net/rubben/Shoppku/public/api/v1/",
            okHttpClient = okHttpClient,
            converterFactory = listOf(GsonConverterFactory.create())
        )
    }
}