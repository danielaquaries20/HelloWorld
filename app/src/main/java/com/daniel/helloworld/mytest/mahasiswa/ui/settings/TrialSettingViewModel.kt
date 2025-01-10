package com.daniel.helloworld.mytest.mahasiswa.ui.settings

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.crocodic.core.data.CoreSession
import com.daniel.helloworld.mytest.mahasiswa.api.ApiAuthService
import com.daniel.helloworld.mytest.mahasiswa.data.UserDao
import com.daniel.helloworld.mytest.mahasiswa.data.response.LogoutResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrialSettingViewModel @Inject constructor(
    private val session: CoreSession,
    private val apiAuthService: ApiAuthService,
    private val userDao: UserDao
) : CoreViewModel() {

    fun logout() = viewModelScope.launch {
        _apiResponse.emit(ApiResponse().responseLoading())
        ApiObserver.run(
            { apiAuthService.logout() },
            false,
            object : ApiObserver.ModelResponseListener<LogoutResponse> {
                override suspend fun onSuccess(response: LogoutResponse) {
                    val userNow = userDao.checkLogin()
                    userNow?.let { userDao.delete(it) }
                    session.setValue(CoreSession.PREF_UID, "")
                    _apiResponse.emit(ApiResponse().responseSuccess())
                }

                override suspend fun onError(response: LogoutResponse) {
                    _apiResponse.emit(ApiResponse().responseError())
                }
            })
    }

    override fun apiLogout() {

    }

    override fun apiRenewToken() {
    }
}