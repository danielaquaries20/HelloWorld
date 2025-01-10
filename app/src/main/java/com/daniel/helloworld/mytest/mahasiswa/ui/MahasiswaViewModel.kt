package com.daniel.helloworld.mytest.mahasiswa.ui

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.crocodic.core.base.adapter.CorePagingSource
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.crocodic.core.data.CoreSession
import com.daniel.helloworld.mytest.mahasiswa.api.ApiAuthService
import com.daniel.helloworld.mytest.mahasiswa.data.Mahasiswa
import com.daniel.helloworld.mytest.mahasiswa.data.MahasiswaDao
import com.daniel.helloworld.mytest.mahasiswa.data.UserDao
import com.daniel.helloworld.mytest.mahasiswa.data.model.Product
import com.daniel.helloworld.mytest.mahasiswa.data.repo.MahasiswaRepository
import com.daniel.helloworld.mytest.mahasiswa.data.repo.product.ProductRepository
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
class MahasiswaViewModel @Inject constructor(
    private val mhsDao: MahasiswaDao,
    private val mahasiswaRepository: MahasiswaRepository,
    private val productRepository: ProductRepository,
    private val coreSession: CoreSession,
    private val apiAuthService: ApiAuthService,
    private val userDao: UserDao
) : CoreViewModel() {


    private val _imageSliderResponse = MutableSharedFlow<List<SlideModel>>()
    val imageSliderResponse = _imageSliderResponse.asSharedFlow()

    fun getSlider() = viewModelScope.launch {
        productRepository.sliderProducts().collect {list ->
            val data = ArrayList<SlideModel>()
            list.forEach {
                data.add(SlideModel(it.image, it.title))
            }
            _imageSliderResponse.emit(data)
        }
    }

    private val _product = MutableSharedFlow<List<Product>>()
    val product = _product.asSharedFlow()

    val queries = MutableStateFlow<Triple<String?, String?, String?>>(Triple(null, null, null))

    fun getPagingProducts(): Flow<PagingData<Product>> {
        return queries.flatMapLatest {
            Pager(
                config = CorePagingSource.config(10),
                pagingSourceFactory = {
                    CorePagingSource(0) { _, limit ->
                        productRepository.pagingProducts(
                            limit,
                            countPage(),
                            "${it.first},${it.second},${it.third}"
                        ).first()
                    }
                }
            ).flow.cachedIn(viewModelScope)
        }
    }

    private fun countPage(): Int {
        val page = coreSession.getInt("page")
        coreSession.setValue("page", page + 10)
        return page
    }

    fun getProduct(keyword: String = "") = viewModelScope.launch {
        productRepository.getProducts(keyword).collect {
            _product.emit(it)
        }
    }

    fun sortProduct(sortBy: String = "", orderBy: String = "") = viewModelScope.launch {
        productRepository.sortProducts(sortBy, orderBy).collect {
            _product.emit(it)
        }
    }

    fun filterProduct(filterBy: String = "") = viewModelScope.launch {
        productRepository.filterProducts(filterBy).collect {
            _product.emit(it)
        }
    }

    private val _mhs = MutableSharedFlow<List<Mahasiswa>>()
    val mhs = _mhs.asSharedFlow()

    fun getMhs(keyword: String) = viewModelScope.launch {
        mahasiswaRepository.searchMhs(keyword).collect {
            _mhs.emit(it)
        }
    }

    fun getMhsById(id: Int) = mhsDao.getItem(id)

    suspend fun insertMhs(mhs: Mahasiswa) {
        mhsDao.insert(mhs)
    }

    suspend fun updateMhs(mhs: Mahasiswa) {
        mhsDao.update(mhs)
    }

    suspend fun deleteMhs(mhs: Mahasiswa) {
        mhsDao.delete(mhs)
    }

    override fun apiLogout() {

    }

    override fun apiRenewToken() {
    }

}