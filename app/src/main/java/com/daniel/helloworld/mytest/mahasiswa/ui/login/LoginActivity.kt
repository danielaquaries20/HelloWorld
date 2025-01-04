package com.daniel.helloworld.mytest.mahasiswa.ui.login

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.base.activity.CoreActivity
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.tos
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityLoginBinding
import com.daniel.helloworld.mytest.mahasiswa.data.UserDao
import com.daniel.helloworld.mytest.mahasiswa.ui.MahasiswaActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : CoreActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {

    var inputEmail = ""
    var inputPassword = ""

    @Inject
    lateinit var session: CoreSession

    @Inject
    lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.activity = this
        binding.btnLogin.setOnClickListener(this)
        binding.btnLoginBiometric.setOnClickListener(this)

        lifecycleScope.launch {
            if (userDao.checkLogin() != null) {
                openActivity<MahasiswaActivity>()
                tos("1")
            } else {
                tos("2")
            }
        }

        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        if (it.status == ApiStatus.LOADING) {
                            progressDialog.show()
                        } else {
                            progressDialog.dismiss()
                        }
                        if (it.status == ApiStatus.SUCCESS) {
                            openActivity<MahasiswaActivity>()
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun validateLogin() {
        if (inputEmail.isEmpty()) {
            binding.inputPhone.error = "Isi Email"
            return
        }

        if (inputPassword.isEmpty()) {
            binding.inputPassword.error = "Isi Password"
            return
        }

        viewModel.login(inputEmail, inputPassword)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnLogin -> validateLogin()
        }
    }

    private val progressDialog by lazy {
        MaterialAlertDialogBuilder(this).apply {
            setView(layoutInflater.inflate(R.layout.dialog_loading, null))
            setCancelable(false)
        }.create().apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            // this.window?.setLayout(view.measuredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }
}