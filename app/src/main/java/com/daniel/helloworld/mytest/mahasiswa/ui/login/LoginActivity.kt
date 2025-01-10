package com.daniel.helloworld.mytest.mahasiswa.ui.login

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.base.activity.CoreActivity
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.snacked
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityLoginBinding
import com.daniel.helloworld.mytest.mahasiswa.data.UserDao
import com.daniel.helloworld.mytest.mahasiswa.ui.MahasiswaActivity
import com.daniel.helloworld.mytest.mahasiswa.ui.settings.TrialSettingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : CoreActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {

    var inputEmail = ""
    var inputPassword = ""

    @Inject
    lateinit var session: CoreSession

    @Inject
    lateinit var userDao: UserDao

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo


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

        binding.btnLoginBiometric.isVisible =
            session.getBoolean(TrialSettingActivity.BIOMETRIC_STATUS)

        lifecycleScope.launch {
            loadingDialog.show("Check Status")
            if (userDao.checkLogin() != null) {
                openActivity<MahasiswaActivity>()
                finish()
            }
            loadingDialog.dismiss()
        }

        observe()

        initBiometric()
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        if (it.status == ApiStatus.LOADING) {
                            loadingDialog.show("Login")
                        } else {
                            loadingDialog.dismiss()
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
            binding.btnLoginBiometric -> biometricLogin()
        }
    }

    private fun initBiometric() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    binding.root.snacked("Biometric error: $errString")
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    binding.root.snacked("Biometric succeeded!")
                    viewModel.login(session.getString(EMAIL), session.getString(PASS))
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    binding.root.snacked("Biometric failed")
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account instead")
            .build()
    }

    private fun biometricLogin() {
        biometricPrompt.authenticate(promptInfo)
    }

    companion object {
        const val EMAIL = "email"
        const val PASS = "password"
        const val TOKEN = "token"
    }

}