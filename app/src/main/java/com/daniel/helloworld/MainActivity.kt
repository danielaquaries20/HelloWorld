package com.daniel.helloworld

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var switchMode: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.coba_resource_layout)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        //btnKirim = findViewById(R.id.btn_kirim)
        switchMode = findViewById(R.id.switch_mode)
        switchMode.setOnCheckedChangeListener { buttonView, isChecked ->
            switchTheme(isChecked)
        }

        /*btnKirim.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)

            alertDialog.setTitle("Alert Dialog")
            alertDialog.setMessage("Kirim Email ke tujuan")

            alertDialog.setPositiveButton("Kirim") {dialog, which ->
                dialog.dismiss()
            }

            val dialog = alertDialog.create()

            dialog.show()
        }*/
    }

    private fun switchTheme(isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }
    }

    fun showAlertDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonLabel: String
    ) {
        val alertDialogBuilder = AlertDialog.Builder(context)

        // Set title dialog
        alertDialogBuilder.setTitle(title)

        // Set pesan dialog
        alertDialogBuilder.setMessage(message)

        // Set tombol positif dan listener-nya
        alertDialogBuilder.setPositiveButton(positiveButtonLabel) { dialog, which ->
            // Lakukan tindakan ketika tombol positif diklik
            dialog.dismiss()
        }

        // Membuat dan menampilkan alert dialog
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}