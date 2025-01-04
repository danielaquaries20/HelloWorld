package com.daniel.helloworld.mytest.mahasiswa.ui.login

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.crocodic.core.data.CoreSession
import com.daniel.helloworld.mytest.mahasiswa.api.ApiAuthService
import com.daniel.helloworld.mytest.mahasiswa.data.UserDao
import com.daniel.helloworld.mytest.mahasiswa.data.response.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val session: CoreSession,
    private val apiAuthService: ApiAuthService,
    private val userDao: UserDao
) : CoreViewModel() {
    override fun apiLogout() {}

    override fun apiRenewToken() {}

    fun login(email: String, pasword: String) = viewModelScope.launch {
        _apiResponse.emit(ApiResponse().responseLoading())
        ApiObserver.run(
            { apiAuthService.login(email, pasword) },
            false,
            object : ApiObserver.ModelResponseListener<UserResponse> {
                override suspend fun onSuccess(response: UserResponse) {
                    userDao.insert(response.user.copy(idDb = 1))
                    _apiResponse.emit(ApiResponse().responseSuccess())
                }

                override suspend fun onError(response: UserResponse) {
                    _apiResponse.emit(ApiResponse().responseError())
                }
            })
    }


}