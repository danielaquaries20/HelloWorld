package com.daniel.helloworld.pertemuan12

import androidx.lifecycle.viewModelScope
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.daniel.helloworld.pertemuan12.data.DataProduct
import com.daniel.helloworld.pertemuan12.database.Friend
import com.daniel.helloworld.pertemuan12.database.FriendDao
import com.daniel.helloworld.pertemuan12.database.repo.FriendRepository
import com.daniel.helloworld.pertemuan12.repository.DataProductsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val friendRepository: FriendRepository,
    private val dataProductsRepo: DataProductsRepo,
    private val friendDao: FriendDao
) : CoreViewModel() {

    private val _product = MutableSharedFlow<List<DataProduct>>()
    val product = _product.asSharedFlow()

    fun getProduct(keyword: String = "") = viewModelScope.launch {
        dataProductsRepo.getProducts(keyword).collect {
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