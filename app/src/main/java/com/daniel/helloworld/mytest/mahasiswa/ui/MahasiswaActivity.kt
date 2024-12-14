package com.daniel.helloworld.mytest.mahasiswa.ui

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
import com.daniel.helloworld.mytest.btm_sht.BottomSheetSorting
import com.daniel.helloworld.mytest.mahasiswa.data.Mahasiswa
import com.daniel.helloworld.mytest.mahasiswa.data.model.Product
import com.daniel.helloworld.mytest.mahasiswa.ui.adapter.RvProductAdapter
import com.daniel.helloworld.mytest.mahasiswa.ui.btm_sht.TestBtmShtFilter
import com.daniel.helloworld.mytest.mahasiswa.ui.btm_sht.TestBtmShtSort
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MahasiswaActivity :
    CoreActivity<ActivityMahasiswaBinding, MahasiswaViewModel>(R.layout.activity_mahasiswa),
    View.OnClickListener {

    private val listMhs = ArrayList<Mahasiswa>()
    private val listProduct = ArrayList<Product>()

    //    private lateinit var adapter: RvMahasiswaAdapter
    private lateinit var adapter: RvProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /*adapter = RvMahasiswaAdapter(this) { pos, data ->

            val destination = Intent(this, TambahMahasiswaActivity::class.java).apply {
                putExtra("id_mhs", data.id)
            }
            startActivity(destination)
        }*/
        adapter = RvProductAdapter(this)
        binding.rvMahasiswa.adapter = adapter


        observe()
        setView()
//        viewModel.getMhs("")
        viewModel.getProduct("")

        binding.etSearch.doOnTextChanged { text, start, before, count ->
//            viewModel.getMhs(text.toString().trim())
            viewModel.getProduct(text.toString().trim())
        }

    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    /*viewModel.mhs.collect { data ->
                        listMhs.clear()
                        listMhs.addAll(data)
                        adapter.setData(listMhs)
                        if (listMhs.isEmpty()) binding.tvEmpty.isVisible = true
                        else binding.tvEmpty.isVisible = false
                    }*/
                    viewModel.product.collect { data ->
                        listProduct.clear()
                        listProduct.addAll(data)
                        adapter.setData(listProduct)
                        if (listProduct.isEmpty()) binding.tvEmpty.isVisible = true
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
        binding.ftbFilter.setOnClickListener(this)
        binding.ftbSort.setOnClickListener(this)
    }

    private fun showBottomSheet() {
        val sortBtmSht = BottomSheetSorting {sortBy, orderBy ->
            // Perintah yang diperlukan
            viewModel.sortProduct(sortBy, orderBy)
        }
        sortBtmSht.show(supportFragmentManager, "Sorting")
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.ftbSort -> {
                val btmShtSort = TestBtmShtSort { sortBy, orderBy ->
                    viewModel.sortProduct(sortBy, orderBy)
                }

                btmShtSort.show(supportFragmentManager, "BtmShtSortingData")
                /*val destination = Intent(this, TambahMahasiswaActivity::class.java)
                startActivity(destination)*/
            }

            binding.ftbFilter -> {
                val btmShtFilter = TestBtmShtFilter { filterBy ->
                    viewModel.filterProduct(filterBy)
                }
                btmShtFilter.show(supportFragmentManager, "BtmShtFilteringData")
            }
        }
    }
}