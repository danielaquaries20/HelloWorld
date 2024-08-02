package com.daniel.helloworld.mytest.tab_view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityTabViewBinding
import com.daniel.helloworld.mytest.tab_view.view_pager.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class TabViewActivity : AppCompatActivity() {


    private lateinit var binding: ActivityTabViewBinding
    /*private lateinit var tlMenu: TabLayout
    private lateinit var viewPager: ViewPager2*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_tab_view)

        binding = ActivityTabViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val adapter = ViewPagerAdapter(this)
        binding.vpBody.adapter = adapter

        TabLayoutMediator(binding.tlMenu, binding.vpBody) { tab, position ->
            tab.text = when (position) {
                0 -> "Home"
                1 -> "Profile"
                else -> null
            }
        }.attach()

    }
}