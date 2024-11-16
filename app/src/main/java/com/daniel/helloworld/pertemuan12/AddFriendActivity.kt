package com.daniel.helloworld.pertemuan12

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.base.activity.CoreActivity
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityAddFriendBinding
import com.daniel.helloworld.pertemuan12.database.Friend
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


@AndroidEntryPoint
class AddFriendActivity :
    CoreActivity<ActivityAddFriendBinding, FriendViewModel>(R.layout.activity_add_friend) {

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

    private var idFriend: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        idFriend = intent.getIntExtra("id", 0)


        if (idFriend != 0) {
            getFriend()
        }

        binding.btnSave.setOnClickListener {
            addData()
        }
        binding.ivPhoto.setOnClickListener {
            openGallery()
        }

        binding.btnDelete.setOnClickListener { delete() }
    }

    private fun getFriend() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getFriendById(idFriend).collect { friend ->
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
            val data: Friend
            if (photoStr.isEmpty()) {
                data = oldFriend!!.copy(
                    name = name,
                    school = school,
                    hobby = hobby
                ).apply {
                    id = idFriend
                }
            } else {
                data = oldFriend!!.copy(
                    name = name,
                    school = school,
                    hobby = hobby,
                    photo = photoStr
                ).apply {
                    id = idFriend
                }
            }

            lifecycleScope.launch {
                viewModel.editFriend(data)
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