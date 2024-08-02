package com.daniel.helloworld.pertemuan10

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityDetailTemanBinding

class DetailTemanActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailTemanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_teman)
//        setContentView(R.layout.activity_detail_teman)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
    }

    private fun initView() {
        binding.namaTeman = intent.getStringExtra("nama")
        binding.sekolahTeman = intent.getStringExtra("sekolah")
        binding.fotoTeman = byteArrayToDrawable(intent.getByteArrayExtra("foto"))
    }

    private fun byteArrayToDrawable(byteArray: ByteArray?): Drawable? {
        return if (byteArray != null) {
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

            val drawable = BitmapDrawable(resources, bitmap)
            drawable
        } else null
    }
}