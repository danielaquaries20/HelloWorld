package com.daniel.helloworld.pertemuan12

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.crocodic.core.base.adapter.CorePagingSource
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.daniel.helloworld.pertemuan12.data.DataProduct
import com.daniel.helloworld.pertemuan12.database.Friend
import com.daniel.helloworld.pertemuan12.database.FriendDao
import com.daniel.helloworld.pertemuan12.database.repo.FriendRepository
import com.daniel.helloworld.pertemuan12.repository.DataProductsRepo
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val friendRepository: FriendRepository,
    private val dataProductsRepo: DataProductsRepo,
    private val friendDao: FriendDao
) : CoreViewModel() {

    val queries = MutableStateFlow<Triple<String?, String?, String?>>(Triple(null, null, null))

    fun getPagingProducts() : Flow<PagingData<DataProduct>> {
        return queries.flatMapLatest {
            Pager(
                config = CorePagingSource.config(10),
                pagingSourceFactory = {
                    CorePagingSource(0) {page: Int, limit: Int ->
                        dataProductsRepo.pagingProducts(limit, page).first()
                    }
                }
            ).flow.cachedIn(viewModelScope)
        }
    }

    private val _slider = MutableSharedFlow<List<SlideModel>>()
    val slider = _slider.asSharedFlow()

    fun getSlider() = viewModelScope.launch {
        dataProductsRepo.getSlider().collect {
            val data = ArrayList<SlideModel>()
            it.forEach {photo->
                data.add(SlideModel(photo.thumbnail, photo.title))
            }
            _slider.emit(data)
        }
    }


    private val _product = MutableSharedFlow<List<DataProduct>>()
    val product = _product.asSharedFlow()

    fun getProduct(keyword: String = "") = viewModelScope.launch {
        dataProductsRepo.getProducts(keyword).collect {
            _product.emit(it)
        }
    }

    fun sortProducts(sortBy: String = "", orderBy: String = "") = viewModelScope.launch {
        dataProductsRepo.sortProducts(sortBy, orderBy).collect {
            _product.emit(it)
        }
    }

    fun filterProducts(filter: String = "") = viewModelScope.launch {
        dataProductsRepo.filterProducts(filter).collect {
            _product.emit(it)
        }
    }


    private val _friends = MutableSharedFlow<List<Friend>>()
    val friends = _friends.asSharedFlow()

    fun getFriend(keyword: String? = null) = viewModelScope.launch {
        friendRepository.searchFriend(keyword).collect {
            it?.let { friends ->
                _friends.emit(friends)
            }
        }
    }

    fun getFriendById(id: Int) = friendDao.getItemById(id)

    suspend fun insertFriend(data: Friend) {
        friendDao.insert(data)

    }

    suspend fun editFriend(data: Friend) {
        friendDao.update(data)
    }

    suspend fun deleteFriend(data: Friend) {
        friendDao.delete(data)
    }

    override fun apiLogout() {
    }

    override fun apiRenewToken() {
    }
}