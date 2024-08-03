package com.daniel.helloworld.pertemuan12

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityListFriendBinding
import com.daniel.helloworld.pertemuan12.database.Friend
import kotlinx.coroutines.launch

class ListFriendActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListFriendBinding

    private var friendList = ArrayList<Friend>()

    private lateinit var viewModel: FriendViewModel
    private lateinit var adapter: AdapterRVFriend

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_friend)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_list_friend)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewModelFactory = FriendVMFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[FriendViewModel::class.java]

        adapter = AdapterRVFriend(this) { position, data ->
            val destination = Intent(this, AddFriendActivity::class.java).apply {
                putExtra("id", data.id)
            }
            startActivity(destination)
        }

        binding.rvShowData.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getFriend().collect { friends ->
                        Log.d("DATABASE", "Friends: $friends")
                        friendList.clear()
                        friendList.addAll(friends)
                        adapter.setData(friendList)
                    }
                }
            }
        }

        binding.ftbnAdd.setOnClickListener {
            val destination = Intent(this, AddFriendActivity::class.java)
            startActivity(destination)
        }

    }

}