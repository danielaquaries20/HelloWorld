package com.daniel.helloworld.pertemuan12

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityAddFriendBinding
import com.daniel.helloworld.pertemuan12.database.Friend
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class AddFriendActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddFriendBinding

    private lateinit var viewModel: FriendViewModel

    private lateinit var photoFile: File

    private var photoStr: String = ""

    private var oldFriend: Friend? = null

    private var galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val parcelFileDescriptor = contentResolver.openFileDescriptor(
                    it?.data?.data
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
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_friend)

        enableEdgeToEdge()
//        setContentView(R.layout.activity_add_friend)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        photoFile = try {
            creteImageFile()
        } catch (ex: IOException) {
            Toast.makeText(this, "Cannot create Image File", Toast.LENGTH_SHORT).show()
            return
        }

        val id = intent.getIntExtra("id", 0)

        val viewModelFactory = FriendVMFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[FriendViewModel::class.java]

        if (id != 0) {
            getFriend(id)
        }

        binding.btnSave.setOnClickListener {
            addData()
        }
        binding.ivPhoto.setOnClickListener {
            openGallery()
        }

        binding.btnDelete.setOnClickListener { delete() }
    }

    private fun getFriend(id: Int) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getFriendById(id).collect { friend ->
                        oldFriend = friend
                        binding.etName.setText(friend?.name)
                        binding.etSchool.setText(friend?.school)
                        binding.etHobby.setText(friend?.hobby)

                        if (friend?.photo?.isNotEmpty() == true) {
                            val photo = stringToBitmap(friend.photo)
                            binding.ivPhoto.setImageBitmap(photo)
                        }

                        binding.btnDelete.isVisible = true
                    }
                }
            }
        }

    }

    private fun delete() {
        lifecycleScope.launch {
            oldFriend?.let { viewModel.deleteFriend(it) }
        }
    }

    private fun addData() {
        val name = binding.etName.text.toString().trim()
        val school = binding.etSchool.text.toString().trim()
        val hobby = binding.etHobby.text.toString().trim()

        if (name.isEmpty() || school.isEmpty() || hobby.isEmpty()) {
            Toast.makeText(this, "Please fill the blank form", Toast.LENGTH_SHORT).show()
            return
        }

        if (oldFriend == null) {
            val data = Friend(name, school, hobby, photoStr)
            lifecycleScope.launch {
                viewModel.insertFriend(data)
            }
        } else {
            if (name == oldFriend?.name && school == oldFriend?.school && hobby == oldFriend?.hobby && photoStr.isEmpty()) {
                Toast.makeText(this, "Data not change", Toast.LENGTH_SHORT).show()
                return
            }

            val data = oldFriend?.copy(
                name = name,
                school = school,
                hobby = hobby,
                photo = photoStr
            )
            Log.d("DataNew", "Data1: $data")
            lifecycleScope.launch {
                Log.d("DataNew", "Data2: $data")
                viewModel.editFriend(data!!)
                Log.d("DataNew", "Data3: $data")
            }
        }


        finish()
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }

    @Throws(IOException::class)
    private fun creteImageFile(): File {
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("PHOTO_", ".jpg", storageDir)
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