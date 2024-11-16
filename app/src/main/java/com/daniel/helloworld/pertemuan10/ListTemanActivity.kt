package com.daniel.helloworld.pertemuan10

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityListTemanBinding
import java.io.ByteArrayOutputStream

class ListTemanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListTemanBinding

    private val data = ArrayList<FriendData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListTemanBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_list_teman)
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
        /*binding.card1.setOnClickListener {
            val namaTeman = binding.tvNama.text.toString().trim()
            val sekolahTeman = binding.tvSekolah.text.toString().trim()
            val fotoTeman = drawableToByteArray(binding.ivFoto.drawable)
            val destination = Intent(this, DetailTemanActivity::class.java)
            with(destination) {
                putExtra("nama", namaTeman)
                putExtra("sekolah", sekolahTeman)
                putExtra("foto", fotoTeman)
            }

            startActivity(destination)
        }

        binding.card2.setOnClickListener {
            val namaTeman = binding.tvNama2.text.toString().trim()
            val sekolahTeman = binding.tvSekolah2.text.toString().trim()
            val fotoTeman = drawableToByteArray(binding.ivFoto2.drawable)
            val destination = Intent(this, DetailTemanActivity::class.java).also {
                it.putExtra("nama", namaTeman)
                it.putExtra("sekolah", sekolahTeman)
                it.putExtra("foto", fotoTeman)
            }


            startActivity(destination)

        }

        binding.card3.setOnClickListener {
            val namaTeman = binding.tvNama3.text.toString().trim()
            val sekolahTeman = binding.tvSekolah3.text.toString().trim()
            val fotoTeman = drawableToByteArray(binding.ivFoto3.drawable)
            val destination = Intent(this, DetailTemanActivity::class.java).apply {
                putExtra("nama", namaTeman)
                putExtra("sekolah", sekolahTeman)
                putExtra("foto", fotoTeman)
            }

            startActivity(destination)

        }*/

        val cloud = ResourcesCompat.getDrawable(resources, R.drawable.cloud, null)
        val rain = ResourcesCompat.getDrawable(resources, R.drawable.cloud_rain, null)
        val sun = ResourcesCompat.getDrawable(resources, R.drawable.sun, null)
        val friendData = arrayOf(
            FriendData("Budi Pamungkas", "SMKN 11 Semarang", cloud),
            FriendData("Tifa Lockhart", "SMKN 1 Kendal", sun),
            FriendData("Kevin Hart", "SMAN 1 Bergas", rain)
        )

        data.addAll(friendData)

        val adapter = RvFriendAdapter(this) { position, data ->
            val friendPhoto = data.photo?.let { drawableToByteArray(it) }
            val destination = Intent(this, DetailTemanActivity::class.java)
            with(destination) {
                putExtra("nama", data.name)
                putExtra("sekolah", data.school)
                putExtra("foto", friendPhoto)
            }

            startActivity(destination)
        }
        adapter.setData(data)

        binding.rvFriend.adapter = adapter
    }

    private fun drawableToByteArray(drawable: Drawable): ByteArray {
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

/*
fun main() {
    val penambahan2 = {x: Int, y: Int -> x+y}
    println("Hasil 1: ${penambahan1(7, 9)}")
    println("Hasil 2: ${penambahan2(7, 9)}")

    var msg : String? = "Saya belajar lambda expression"

    if (msg != null) println(msg)
    msg?.let { println(it) }
    msg?.run { println(this) }
}

fun penambahan1(x: Int, y: Int) : Int {
    return x + y
}*/
