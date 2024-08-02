package com.daniel.helloworld.mytest.permission

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityTestPermBinding

class TestPermActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestPermBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_perm)

        enableEdgeToEdge()
//        setContentView(R.layout.activity_test_perm)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnContact.setOnClickListener {
            val destination = Intent(this, TestPermissionActivity::class.java)
            startActivity(destination)
        }

        binding.btnCameraGallery.setOnClickListener {
            val destination = Intent(this, TestGetPhotoActivity::class.java)
            startActivity(destination)
        }
    }
}