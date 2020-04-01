package com.accenture.socialdistancing.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class OnBoardingAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var fragmentList: MutableList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment = fragmentList[position]


    override fun getCount() = fragmentList.size
    fun addFragments(fragment: Fragment) = fragmentList.add(fragment)
}