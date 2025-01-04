package com.daniel.helloworld.pertemuan12

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.base.activity.CoreActivity
import com.crocodic.core.base.adapter.PaginationAdapter
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.toJson
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityListFriendBinding
import com.daniel.helloworld.databinding.ItemFriendBinding
import com.daniel.helloworld.pertemuan12.btm_sht.BottomSheetFilterProducts
import com.daniel.helloworld.pertemuan12.btm_sht.BottomSheetSortingProducts
import com.daniel.helloworld.pertemuan12.data.DataProduct
import com.daniel.helloworld.pertemuan12.database.Friend
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListFriendActivity :
    CoreActivity<ActivityListFriendBinding, FriendViewModel>(R.layout.activity_list_friend) {

    private var friendList = ArrayList<Friend>()
    private var productList = ArrayList<DataProduct>()

    private lateinit var adapter: AdapterRVFriend

    @Inject
    lateinit var gson: Gson

    private val adapterCore by lazy {
        PaginationAdapter<ItemFriendBinding, DataProduct>(R.layout.item_friend).initItem { position, data ->
            openActivity<DetailProductActivity> {
                val dataProduct = data.toJson(gson)
                putExtra(DetailProductActivity.DATA, dataProduct)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /*adapter = AdapterRVFriend(this) { position, data ->
            val destination = Intent(this, AddFriendActivity::class.java).apply {
                putExtra("id", data.id)
            }
            startActivity(destination)
        }*/
//        adapter = AdapterRVFriend(this)
        binding.rvShowData.adapter = adapterCore

//        viewModel.getFriend()
        lifecycleScope.launch {
            viewModel.queries.emit(Triple("", "", ""))
        }
        viewModel.getSlider()


        binding.etSearch.doOnTextChanged { text, start, before, count ->
            val keyword = "%${text.toString().trim()}%"
//            viewModel.getFriend(keyword)
            viewModel.getProduct(keyword)
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                /*launch {
                    viewModel.friends.collect { friends ->
                        friendList.clear()
                        friendList.addAll(friends)
                        adapter.setData(friendList)
                    }
                }*/
                launch {
                    viewModel.slider.collect { data ->
                        binding.ivSlider.setImageList(data)
                    }
                }

                launch {
                    viewModel.getPagingProducts().collectLatest { data ->
                        adapterCore.submitData(data)
                    }
                }

                /*launch {
                    viewModel.friends.collect { friends ->
                        friendList.clear()
                        friendList.addAll(friends)
                        adapter.setData(friendList)
                    }
                }*/
            }
        }

        binding.ftbFilter.setOnClickListener {
            val btmSht = BottomSheetFilterProducts { filter ->
                viewModel.filterProducts(filter)
            }

            btmSht.show(supportFragmentManager, "BtmShtFilteringProducts")
        }


        binding.ftbSort.setOnClickListener {
            val btmSht = BottomSheetSortingProducts { sortBy, order ->
                viewModel.sortProducts(sortBy, order)
            }

            btmSht.show(supportFragmentManager, "BtmShtSortingProducts")
        }

    }

}