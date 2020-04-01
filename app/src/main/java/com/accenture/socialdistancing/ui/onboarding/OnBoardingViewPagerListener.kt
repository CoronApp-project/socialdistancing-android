package com.accenture.socialdistancing.ui.onboarding

import androidx.viewpager.widget.ViewPager

class OnBoardingViewPagerListener(private val closure: (Int) -> Unit) : ViewPager.OnPageChangeListener {

    override fun onPageScrollStateChanged(p0: Int) {
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
    }

    override fun onPageSelected(position: Int) = closure(position)
}