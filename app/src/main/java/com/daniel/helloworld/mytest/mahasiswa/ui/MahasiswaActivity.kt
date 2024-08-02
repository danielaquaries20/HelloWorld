package com.daniel.helloworld.mytest.mahasiswa.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
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
import com.daniel.helloworld.databinding.ActivityMahasiswaBinding
import com.daniel.helloworld.mytest.mahasiswa.data.Mahasiswa
import kotlinx.coroutines.launch

class MahasiswaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMahasiswaBinding

    private lateinit var viewModel: MahasiswaViewModel

    private val listMhs = ArrayList<Mahasiswa>()

    private lateinit var adapter: RvMahasiswaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mahasiswa)
//        setContentView(R.layout.activity_mahasiswa)

        val viewModelFactory = MahasiswaViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[MahasiswaViewModel::class.java]

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /*val database = AppDatabase.getDatabase(this)
        val dao = database.mahasiswaDao()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    dao.getAllItems().collect{
                        Log.d("ListMhs", "V1: $it")
                        listMhs.clear()
                        listMhs.addAll(it)
                        setData()
                        Log.d("ListMhs", "V2: $listMhs")
                    }
                }
            }
        }*/

        adapter = RvMahasiswaAdapter(this) { pos, data ->

            val destination = Intent(this, TambahMahasiswaActivity::class.java).apply {
                putExtra("id_mhs", data.id)
            }
            startActivity(destination)
        }
        binding.rvMahasiswa.adapter = adapter

        observe()
        setView()


    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getMhs().collect { data ->
                        data?.let {
                            listMhs.clear()
                            listMhs.addAll(it)
                            adapter.setData(listMhs)
                            if (listMhs.isEmpty()) binding.tvEmpty.isVisible = true
                            else binding.tvEmpty.isVisible = false
                        }
                    }
                }
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