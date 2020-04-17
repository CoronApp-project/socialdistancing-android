package com.accenture.socialdistancing.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.accenture.socialdistancing.R
import com.accenture.socialdistancing.ui.onboarding.OnBoardingAdapter
import com.accenture.socialdistancing.ui.onboarding.OnBoardingViewPagerListener
import com.accenture.socialdistancing.ui.onboarding.tutorialitems.FirstItemTutorial
import com.accenture.socialdistancing.ui.onboarding.tutorialitems.SecondItemTutorial
import com.accenture.socialdistancing.ui.onboarding.tutorialitems.ThirdItemTutorial
import kotlinx.android.synthetic.main.activity_on_boarding.*

class HelpFragment : Fragment() {

    private lateinit var onBoardingAdapter: OnBoardingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_help, container, false)

    }

    override fun onResume() {
        super.onResume()
        onViewPagerInit()
    }

    private fun addPagerFragments() {
        onBoardingAdapter.addFragments(FirstItemTutorial())
        onBoardingAdapter.addFragments(SecondItemTutorial())
        onBoardingAdapter.addFragments(ThirdItemTutorial())
    }

    private fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                firstDotImageView.setImageResource(R.drawable.current_position_icon)
                secondDotImageView.setImageResource(R.drawable.disable_position_icon)
                thirdDotImageView.setImageResource(R.drawable.disable_position_icon)
            }
            1 -> {
                firstDotImageView.setImageResource(R.drawable.disable_position_icon)
                secondDotImageView.setImageResource(R.drawable.current_position_icon)
                thirdDotImageView.setImageResource(R.drawable.disable_position_icon)
            }
            2 -> {
                firstDotImageView.setImageResource(R.drawable.disable_position_icon)
                secondDotImageView.setImageResource(R.drawable.disable_position_icon)
                thirdDotImageView.setImageResource(R.drawable.current_position_icon)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        onViewPagerInit()
    }

    override fun onStop() {
        super.onStop()
        onViewPagerInit()
    }

    private fun onViewPagerInit() {
        onBoardingAdapter = OnBoardingAdapter(activity!!.supportFragmentManager)
        addPagerFragments()
        tutorialViewPager.adapter = onBoardingAdapter
        tutorialViewPager.addOnPageChangeListener(OnBoardingViewPagerListener(this::onPageSelected))
    }
}