package com.daniel.helloworld.di.module

import android.content.Context
import com.crocodic.core.data.CoreSession
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideCoreSession(@ApplicationContext context: Context) = CoreSession(context)
}