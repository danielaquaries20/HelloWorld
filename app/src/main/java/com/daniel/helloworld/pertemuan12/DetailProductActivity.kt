package com.daniel.helloworld.pertemuan12

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.crocodic.core.base.activity.NoViewModelActivity
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityDetailProductBinding
import com.daniel.helloworld.pertemuan12.data.DataProduct
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailProductActivity :
    NoViewModelActivity<ActivityDetailProductBinding>(R.layout.activity_detail_product) {

    @Inject
    lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val dataIntent = intent.getStringExtra(DATA)
        binding.data = gson.fromJson(dataIntent, DataProduct::class.java)

    }

    companion object {
        const val DATA = "data"
    }
}