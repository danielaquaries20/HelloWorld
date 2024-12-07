package com.daniel.helloworld.pertemuan12

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.base.activity.CoreActivity
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityListFriendBinding
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
        adapter = AdapterRVFriend(this)

//        viewModel.getFriend()
        viewModel.getProduct()
        binding.etSearch.doOnTextChanged { text, start, before, count ->
            val keyword = "%${text.toString().trim()}%"
//            viewModel.getFriend(keyword)
            viewModel.getProduct(keyword)
        }

        binding.rvShowData.adapter = adapter

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
                        productList.clear()
                        productList.addAll(data)
                        adapter.setData(productList)
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

        binding.ftbnAdd.setOnClickListener {
            val destination = Intent(this, AddFriendActivity::class.java)
            startActivity(destination)
        }

    }

}