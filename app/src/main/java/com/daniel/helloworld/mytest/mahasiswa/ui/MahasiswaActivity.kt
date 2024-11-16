package com.daniel.helloworld.mytest.mahasiswa.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.base.activity.CoreActivity
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityMahasiswaBinding
import com.daniel.helloworld.mytest.mahasiswa.data.Mahasiswa
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MahasiswaActivity :
    CoreActivity<ActivityMahasiswaBinding, MahasiswaViewModel>(R.layout.activity_mahasiswa),
    View.OnClickListener {

    private val listMhs = ArrayList<Mahasiswa>()

    private lateinit var adapter: RvMahasiswaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = RvMahasiswaAdapter(this) { pos, data ->

            val destination = Intent(this, TambahMahasiswaActivity::class.java).apply {
                putExtra("id_mhs", data.id)
            }
            startActivity(destination)
        }
        binding.rvMahasiswa.adapter = adapter


        observe()
        setView()
        viewModel.getMhs("")

        binding.etSearch.doOnTextChanged { text, start, before, count ->
            viewModel.getMhs(text.toString().trim())
        }

    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.mhs.collect { data ->
                        listMhs.clear()
                        listMhs.addAll(data)
                        adapter.setData(listMhs)
                        if (listMhs.isEmpty()) binding.tvEmpty.isVisible = true
                        else binding.tvEmpty.isVisible = false
                    }
                }
                /*launch {
                    viewModel.getMhs().collect { data ->
                        data?.let {
                            listMhs.clear()
                            listMhs.addAll(it)
                            adapter.setData(listMhs)
                            if (listMhs.isEmpty()) binding.tvEmpty.isVisible = true
                            else binding.tvEmpty.isVisible = false
                        }
                    }
                }*/
            }
        }
    }

    /* private fun setData() {
         var mhsStr = ""
         listMhs.forEach {
             mhsStr += "${it.id} - ${it.nim} - ${it.nama}\n\n"
         }

         binding.tvData.text = mhsStr
     }*/

    private fun setView() {
        binding.ftbAdd.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.ftbAdd -> {
                val destination = Intent(this, TambahMahasiswaActivity::class.java)
                startActivity(destination)
            }
        }
    }
}