package com.daniel.helloworld.pertemuan13

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityGetContactBinding

class GetContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGetContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_get_contact)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_get_contact)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnGetContact.setOnClickListener {
            checkPermissionContact()
        }
    }

    private fun checkPermissionContact() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getContacts()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 100)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                getContacts()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getContacts() {
        var data = ""
        var cData = 0
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val cursor = contentResolver.query(uri, null, null, null, null)

        if ((cursor?.count ?: 0) > 0) {
            while (cursor?.moveToNext() == true) {
                val name = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        ?: return
                )
                cData++

                val ctn = 290
                if (cData in ctn..ctn + 9) {
                    data += "${cData - ctn + 1} - $name\n\n"
                }
            }
            cursor?.close()

            binding.tvContact.text = data
        } else {
            data = "No Contact Found"
            binding.tvContact.text = data
            Toast.makeText(this, "No Contact Found", Toast.LENGTH_SHORT).show()
        }
    }

}