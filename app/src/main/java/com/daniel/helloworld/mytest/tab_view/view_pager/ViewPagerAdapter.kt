package com.daniel.helloworld.mytest.tab_view.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.daniel.helloworld.mytest.tab_view.frag_menu.HomeFragment
import com.daniel.helloworld.mytest.tab_view.frag_menu.ProfileFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> ProfileFragment.newInstance("Miachael Hoxa", "Stanford University")
            else -> throw IllegalStateException("Invalid position")
        }
    }
}