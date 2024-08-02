package com.daniel.helloworld.pertemuan9

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.daniel.helloworld.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MenuHomeActivity : AppCompatActivity() {

    private lateinit var tabMenu : TabLayout
    private lateinit var viewPager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Siklus", "onCreate")
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tabMenu = findViewById(R.id.tab_menu)
        viewPager = findViewById(R.id.view_pager)

        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        /*TabLayoutMediator(tabMenu, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Home"
                1 -> "Profile"
                else -> null
            }
        }.attach()*/

        TabLayoutMediator(tabMenu, viewPager) {menu, pos ->
            menu.text = when (pos) {
                0 -> "Chat"
                else -> "Status"
            }
        }.attach()


    }

    override fun onStart() {
        super.onStart()
        Log.d("Siklus", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Siklus", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Siklus", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Siklus", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Siklus", "onDestroy")
    }
}