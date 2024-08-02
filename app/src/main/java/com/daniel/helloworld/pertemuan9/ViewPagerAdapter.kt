package com.daniel.helloworld.pertemuan9

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.daniel.helloworld.pertemuan9.fragments.ChatFragment
import com.daniel.helloworld.pertemuan9.fragments.StatusFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ChatFragment()
            1 -> StatusFragment.newInstance("Budi Kharisma", "Sedang belajar koding")
            else -> throw IllegalStateException("Invalid position")
        }
    }
}