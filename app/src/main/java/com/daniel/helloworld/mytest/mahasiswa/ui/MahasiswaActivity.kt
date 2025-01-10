package com.daniel.helloworld.mytest.mahasiswa.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.base.activity.CoreActivity
import com.crocodic.core.base.adapter.PaginationAdapter
import com.crocodic.core.base.adapter.PaginationLoadState
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.openActivity
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityMahasiswaBinding
import com.daniel.helloworld.databinding.ItemMahasiswaBinding
import com.daniel.helloworld.mytest.btm_sht.BottomSheetSorting
import com.daniel.helloworld.mytest.mahasiswa.data.UserDao
import com.daniel.helloworld.mytest.mahasiswa.data.model.Product
import com.daniel.helloworld.mytest.mahasiswa.ui.detail.DetailMahasiswaActivity
import com.daniel.helloworld.mytest.mahasiswa.ui.settings.TrialSettingActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MahasiswaActivity :
    CoreActivity<ActivityMahasiswaBinding, MahasiswaViewModel>(R.layout.activity_mahasiswa),
    View.OnClickListener {

    /*private val listMhs = ArrayList<Mahasiswa>()
    private val listProduct = ArrayList<Product>()*/

    @Inject
    lateinit var coreSession: CoreSession

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var userDao: UserDao

    /*private lateinit var adapter: RvMahasiswaAdapter
    private lateinit var adapter: RvProductAdapter*/

    private val adapterCore by lazy {
        PaginationAdapter<ItemMahasiswaBinding, Product>(R.layout.item_mahasiswa).initItem { position, data ->
            openActivity<DetailMahasiswaActivity> {
                val jsonData = gson.toJson(data)
                putExtra(DetailMahasiswaActivity.DATA, jsonData)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(adapterCore) {
            binding.rvMahasiswa.adapter = withLoadStateFooter(PaginationLoadState.default)
        }

        observe()
        setView()

        viewModel.getSlider()
        lifecycleScope.launch {
            viewModel.queries.emit(Triple("title", "description", "thumbnail"))
        }

        /*binding.etSearch.doOnTextChanged { text, start, before, count ->
//            viewModel.getMhs(text.toString().trim())
            viewModel.getProduct(text.toString().trim())
        }*/

        /*enableEdgeToEdge()
       ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
           val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
           v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
           insets
       }*/

        /*adapter = RvMahasiswaAdapter(this) { pos, data ->

            val destination = Intent(this, TambahMahasiswaActivity::class.java).apply {
                putExtra("id_mhs", data.id)
            }
            startActivity(destination)
        }*/
//        adapter = RvProductAdapter(this)
//        binding.rvMahasiswa.adapter = adapterCore

//        viewModel.getMhs("")
//        viewModel.getProduct()
    }

    override fun onStart() {
        super.onStart()
        coreSession.setValue("page", 0)
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getPagingProducts().collectLatest { data ->
                        adapterCore.submitData(data)
                    }

                    /*viewModel.mhs.collect { data ->
                        listMhs.clear()
                        listMhs.addAll(data)
                        adapter.setData(listMhs)
                        if (listMhs.isEmpty()) binding.tvEmpty.isVisible = true
                        else binding.tvEmpty.isVisible = false
                    }*/
                    /*viewModel.product.collect { data ->
                        listProduct.clear()
                        listProduct.addAll(data)
                        adapter.setData(listProduct)
                        adapterCore.submitList(data)
                        if (data.isEmpty()) binding.tvEmpty.isVisible = true
                        else binding.tvEmpty.isVisible = false
                    }*/
                }
                launch {
                    viewModel.imageSliderResponse.collect {
                        binding.ivSlider.setImageList(it)
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
        binding.btnSetting.setOnClickListener(this)
//        binding.ftbSort.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnSetting -> {
                openActivity<TrialSettingActivity>()
//                viewModel.logout()
                /*val btmShtSort = TestBtmShtSort { sortBy, orderBy ->
                    viewModel.sortProduct(sortBy, orderBy)
                }

                btmShtSort.show(supportFragmentManager, "BtmShtSortingData")*/
                /*val destination = Intent(this, TambahMahasiswaActivity::class.java)
                startActivity(destination)*/
            }

            /* binding.ftbFilter -> {
                 val btmShtFilter = TestBtmShtFilter { filterBy ->
                     viewModel.filterProduct(filterBy)
                 }
                 btmShtFilter.show(supportFragmentManager, "BtmShtFilteringData")
             }*/
        }
    }

    private fun showBottomSheet() {
        val sortBtmSht = BottomSheetSorting { sortBy, orderBy ->
            // Perintah yang diperlukan
            viewModel.sortProduct(sortBy, orderBy)
        }
        sortBtmSht.show(supportFragmentManager, "Sorting")
    }

}