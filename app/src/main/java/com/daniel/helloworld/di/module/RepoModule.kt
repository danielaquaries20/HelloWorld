package com.daniel.helloworld.di.module

import com.daniel.helloworld.pertemuan12.database.repo.FriendRepository
import com.daniel.helloworld.pertemuan12.database.repo.ImplFriendRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepoModule {

    @Singleton
    @Binds
    abstract fun bindFriendRepository(implFriendRepository: ImplFriendRepository): FriendRepository
}