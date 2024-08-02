package com.daniel.helloworld.mytest.mahasiswa.ui.detail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityListMahasiwaBinding
import com.daniel.helloworld.mytest.mahasiswa.ui.detail.adapter.RvMhsAdapter
import com.daniel.helloworld.mytest.mahasiswa.ui.detail.model.Mhs
import java.io.ByteArrayOutputStream

class ListMahasiwaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListMahasiwaBinding

    private val listMhs = ArrayList<Mhs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMahasiwaBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_list_mahasiwa)
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

        val cloud = ResourcesCompat.getDrawable(resources, R.drawable.cloud, null)
        val rain = ResourcesCompat.getDrawable(resources, R.drawable.cloud_rain, null)
        val sun = ResourcesCompat.getDrawable(resources, R.drawable.sun, null)
        val list = arrayOf(
            Mhs("Budi Pamungkas", "SMKN 11 Semarang", cloud),
            Mhs("Michael Hoxa", "SMKN 1 Kendal", sun),
            Mhs("Rika Takanashi", "SMKN 10 Bergas", rain)
        )
        listMhs.addAll(list)

        val adapter = RvMhsAdapter(this) { pos, data ->
            val photo = drawableToByteArray(data.photo)

            val destination = Intent(this, DetailMahasiswaActivity::class.java).apply {
                putExtra("name", data.name)
                putExtra("school", data.school)
                putExtra("photo", photo)
            }
            startActivity(destination)
        }
        adapter.setData(listMhs)
        Log.d("listData", "${listMhs}")
        binding.rvMahasiswa.adapter = adapter

    }


    private fun drawableToByteArray(drawable: Drawable?): ByteArray? {
        if (drawable == null) return null

        var bitmap: Bitmap
        if (drawable is BitmapDrawable) {
            bitmap = drawable.bitmap
        }

        bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = android.graphics.Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        return stream.toByteArray()
    }

}