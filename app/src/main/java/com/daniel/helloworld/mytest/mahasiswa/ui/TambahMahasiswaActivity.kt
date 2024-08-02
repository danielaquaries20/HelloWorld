package com.daniel.helloworld.mytest.mahasiswa.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityTambahMahasiswaBinding
import com.daniel.helloworld.mytest.mahasiswa.data.Mahasiswa
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class TambahMahasiswaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityTambahMahasiswaBinding

    private lateinit var viewModel: MahasiswaViewModel

    private lateinit var photoFile: File

    private var photoStr: String = ""

    private var oldMahasiswa: Mahasiswa? = null

    private var cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
                binding.ivPhoto.setImageBitmap(takenImage)
                photoStr = bitmapToString(takenImage)
                Log.d("DataPhoto", "Photo: $photoStr")
            }
        }

    private var galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val parcelFileDescriptor = contentResolver.openFileDescriptor(
                    result?.data?.data
                        ?: return@registerForActivityResult, "r"
                )
                val fileDescriptor = parcelFileDescriptor?.fileDescriptor
                val inputStream = FileInputStream(fileDescriptor)

                val outputStream = FileOutputStream(photoFile)

                inputStream.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }

                parcelFileDescriptor?.close()

                val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
                binding.ivPhoto.setImageBitmap(takenImage)
                photoStr = bitmapToString(takenImage)
                Log.d("DataPhoto", "Photo: $photoStr")

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tambah_mahasiswa)
        //setContentView(R.layout.activity_tambah_mahasiswa)
        val viewModelFactory = MahasiswaViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[MahasiswaViewModel::class.java]

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setView()
        val idMhs = intent.getIntExtra("id_mhs", 0)

        if (idMhs > 0) {
            getMhs(idMhs)
            binding.btnDelete.visibility = View.VISIBLE
            binding.btnDelete.setOnClickListener(this)
        }
    }

    private fun getMhs(id: Int) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getMhsById(id).collect {
                        /*try {

                        } catch (e:Exception) {
                            e.printStackTrace()
                        }*/
                        oldMahasiswa = it

                        binding.etNim.setText(it?.nim)
                        binding.etName.setText(it?.nama)
                        binding.etDateBorn.setText(it?.tglLahir)
                        binding.etAddress.setText(it?.alamat)
                        binding.etPhone.setText(it?.telepon)

                        if (it?.photo?.isNotEmpty() == true) {
                            val photoBitmap = stringToBitmap(it.photo)
                            photoBitmap?.let { bitmap ->
                                binding.ivPhoto.setImageBitmap(bitmap)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setView() {

        photoFile = try {
            creteImageFile()
        } catch (ex: IOException) {
            Toast.makeText(this, "Cannot create Image File", Toast.LENGTH_SHORT).show()
            return
        }

        binding.btnSave.setOnClickListener(this)
        binding.ivPhoto.setOnClickListener(this)
    }

    private fun saveMhs() {
        val nim = binding.etNim.text.toString().trim()
        val name = binding.etName.text.toString().trim()
        val born = binding.etDateBorn.text.toString().trim()
        val addr = binding.etAddress.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()

        if (nim.isEmpty() || name.isEmpty() || born.isEmpty() || addr.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Isi form yang kosong", Toast.LENGTH_SHORT).show()
            return
        }

        if (oldMahasiswa == null) {
            val newMhs = Mahasiswa(0, nim, name, born, addr, phone, photoStr)
            lifecycleScope.launch {
                viewModel.insertMhs(newMhs)
            }
        } else {

            if (nim == oldMahasiswa?.nim && name == oldMahasiswa?.nama && born == oldMahasiswa?.tglLahir && addr == oldMahasiswa?.alamat && phone == oldMahasiswa?.telepon && photoStr.isEmpty()) {
                Toast.makeText(this, "Data tidak berubah", Toast.LENGTH_SHORT).show()
                return
            }
            val newMhs = oldMahasiswa!!.copy(
                nim = nim,
                nama = name,
                tglLahir = born,
                alamat = addr,
                telepon = phone,
                photo = photoStr
            )

            lifecycleScope.launch {
                viewModel.updateMhs(newMhs)
            }

        }


        finish()
    }

    private fun delete() {
        lifecycleScope.launch {
            viewModel.deleteMhs(oldMahasiswa!!)
        }
        finish()
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnSave -> saveMhs()
            binding.ivPhoto -> getGallery()
            binding.btnDelete -> delete()
        }
    }

    private fun getGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        try {
            galleryLauncher.launch(galleryIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Cannot use Gallery", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun creteImageFile(): File {
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("PHOTO_", ".jpg", storageDir)
    }

    private fun takePicture() {
        val photoUri =
            FileProvider.getUriForFile(this, "com.daniel.helloworld.fileprovider", photoFile)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        }

        try {
            cameraLauncher.launch(cameraIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Cannot use Camera", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    fun bitmapToString(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun stringToBitmap(encodedString: String): Bitmap? {
        return try {
            val byteArray = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        }
    }
}