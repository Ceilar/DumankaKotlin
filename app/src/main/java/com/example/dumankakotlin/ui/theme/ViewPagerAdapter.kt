package com.example.dumankakotlin

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return DumankaFragment()
            1 -> return Reshavanka()
        }
        return DumankaFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}
