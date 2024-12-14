package com.daniel.helloworld.pertemuan12

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.base.activity.CoreActivity
import com.crocodic.core.base.adapter.ReactiveListAdapter
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityListFriendBinding
import com.daniel.helloworld.databinding.ItemFriendBinding
import com.daniel.helloworld.pertemuan12.btm_sht.BottomSheetFilterProducts
import com.daniel.helloworld.pertemuan12.btm_sht.BottomSheetSortingProducts
import com.daniel.helloworld.pertemuan12.data.DataProduct
import com.daniel.helloworld.pertemuan12.database.Friend
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFriendActivity :
    CoreActivity<ActivityListFriendBinding, FriendViewModel>(R.layout.activity_list_friend) {

    private var friendList = ArrayList<Friend>()
    private var productList = ArrayList<DataProduct>()

    private lateinit var adapter: AdapterRVFriend

    private val adapterCore by lazy {
        ReactiveListAdapter<ItemFriendBinding, DataProduct>(R.layout.item_friend)
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
        viewModel.getProduct()
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
                    viewModel.product.collect { data ->
                        Log.d("API", "Data Response: ${data}")

                        adapterCore.submitList(data)
//                        productList.clear()
//                        productList.addAll(data)
//                        adapter.setData(productList)
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

        binding.ftbnFilter.setOnClickListener {
            val btmSht = BottomSheetFilterProducts { filter ->
                viewModel.filterProducts(filter)
            }

            btmSht.show(supportFragmentManager, "BtmShtFilteringProducts")
        }


        binding.ftbnSort.setOnClickListener {
            val btmSht = BottomSheetSortingProducts { sortBy, order ->
                viewModel.sortProducts(sortBy, order)
            }

            btmSht.show(supportFragmentManager, "BtmShtSortingProducts")
        }

    }

}