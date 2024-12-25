package com.daniel.helloworld.mytest.mahasiswa.ui.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.crocodic.core.base.activity.NoViewModelActivity
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityDetailMahasiswaBinding
import com.daniel.helloworld.mytest.mahasiswa.data.model.Product
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailMahasiswaActivity :
    NoViewModelActivity<ActivityDetailMahasiswaBinding>(R.layout.activity_detail_mahasiswa) {

    /*private lateinit var tvName: TextView
    private lateinit var tvSchool: TextView
    private lateinit var ivPhoto: ImageView*/

//    private lateinit var binding: ActivityDetailMahasiswaBinding

    @Inject
    lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_mahasiswa)
//        setContentView(R.layout.activity_detail_mahasiswa)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val jsonData = intent.getStringExtra(DATA)

        binding.data = gson.fromJson(jsonData, Product::class.java)

        /*tvName = findViewById(R.id.tv_name)
        tvSchool = findViewById(R.id.tv_school)
        ivPhoto = findViewById(R.id.iv_photo)*/

        /* binding.nameStudent = intent.getStringExtra("name")
         binding.school = intent.getStringExtra("school")
         binding.photo = byteArrayToDrawable(intent.getByteArrayExtra("photo"))*/
//        if (photo != null) binding.ivPhoto.setImageDrawable(photo)

    }

    /*private fun byteArrayToDrawable(byteArray: ByteArray?): Drawable? {
        return if (byteArray != null) {
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

            val drawable = BitmapDrawable(resources, bitmap)
            drawable
        } else null
    }*/

    companion object {
        const val DATA = "data"
    }
}