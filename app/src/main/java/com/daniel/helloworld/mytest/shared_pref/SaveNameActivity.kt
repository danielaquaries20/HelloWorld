package com.daniel.helloworld.mytest.shared_pref

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivitySaveNameBinding

class SaveNameActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaveNameBinding

    private lateinit var session: MySession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
    }

    private fun initView() {
        session = MySession(this)
        val name = session.getName()
        binding.tvName.text = name

        binding.btnSave.setOnClickListener {
            if (binding.etName.text.toString().isEmpty()) return@setOnClickListener

            session.saveName(binding.etName.text.toString())
            binding.tvName.text = binding.etName.text.toString()
        }
    }
}