package com.daniel.helloworld.di.module

import com.daniel.helloworld.mytest.mahasiswa.data.repo.MahasiswaRepository
import com.daniel.helloworld.mytest.mahasiswa.data.repo.MahasiswaRepositoryImpl
import com.daniel.helloworld.mytest.mahasiswa.data.repo.product.ProductRepository
import com.daniel.helloworld.mytest.mahasiswa.data.repo.product.ProductRepositoryImpl
import com.daniel.helloworld.pertemuan12.database.repo.FriendRepository
import com.daniel.helloworld.pertemuan12.database.repo.ImplFriendRepository
import com.daniel.helloworld.pertemuan12.repository.DataProductsRepo
import com.daniel.helloworld.pertemuan12.repository.ImplDataProductRepo
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

    @Singleton
    @Binds
    abstract fun bindMahasiswaRepository(mahasiswaRepositoryImpl: MahasiswaRepositoryImpl): MahasiswaRepository

    @Singleton
    @Binds
    abstract fun bindProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository


    @Singleton
    @Binds
    abstract fun bindDataProductRepo(implDataProductRepo: ImplDataProductRepo) : DataProductsRepo
}