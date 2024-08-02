package com.daniel.helloworld.mytest.mahasiswa.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityTambahMahasiswaBinding
import com.daniel.helloworld.mytest.mahasiswa.data.Mahasiswa
import kotlinx.coroutines.launch

class TambahMahasiswaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityTambahMahasiswaBinding

    private lateinit var viewModel: MahasiswaViewModel

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
    }

    private fun setView() {
        binding.btnSave.setOnClickListener(this)
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

        val newMhs = Mahasiswa(0, nim, name, born, addr, phone)
        lifecycleScope.launch {
            viewModel.insertMhs(newMhs)
        }
        finish()
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnSave -> saveMhs()
        }
    }
}