package com.daniel.helloworld.mytest.looping_view

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginBottom
import androidx.core.view.setMargins
import com.daniel.helloworld.R

class LoopingViewActivity : AppCompatActivity() {

    private lateinit var btnShow: Button
    private lateinit var etSum: EditText
    private lateinit var container: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_looping_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnShow = findViewById(R.id.btn_show)
        etSum = findViewById(R.id.et_sum)
        container = findViewById(R.id.linear_container)

        btnShow.setOnClickListener {
            showView()
        }

    }

    private fun showView() {
        container.removeAllViews()
        var sum = etSum.text.trim().toString()
        if (sum.isEmpty()) {
            etSum.error = "Masukkan jumlah perulangan"
            return
        }

        for (i in 1..sum.toInt()) {
            val image = ImageView(this)
            image.setImageResource(R.drawable.coc) // Ganti dengan resource gambar Anda
            val imageParams = LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT,
                250,
                250
            )
            imageParams.setMargins(0, 8, 0, 8)
            imageParams.gravity = Gravity.CENTER

            image.layoutParams = imageParams
            container.addView(image)
        }
    }
}