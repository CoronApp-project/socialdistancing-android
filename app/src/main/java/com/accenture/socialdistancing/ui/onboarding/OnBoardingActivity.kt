package com.accenture.socialdistancing.ui.onboarding

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.accenture.socialdistancing.R
import com.accenture.socialdistancing.ui.onboarding.tutorialitems.FirstItemTutorial
import com.accenture.socialdistancing.ui.onboarding.tutorialitems.SecondItemTutorial
import com.accenture.socialdistancing.ui.onboarding.tutorialitems.ThirdItemTutorial
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var onBoardingAdapter: OnBoardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        onBoardingAdapter = OnBoardingAdapter(supportFragmentManager)
        addPagerFragments()
        tutorialViewPager.adapter = onBoardingAdapter
        tutorialViewPager.addOnPageChangeListener(OnBoardingViewPagerListener(this::onPageSelected))
        getStartedButton.setOnClickListener {
            if (getStartedButton.text == getString(R.string.next_tutorial_button)) {
                val currentPosition = tutorialViewPager.currentItem
                tutorialViewPager.currentItem = currentPosition + 1
            } else {
                Toast.makeText(this, "Go To App", Toast.LENGTH_LONG).show()
            }
        }
        skipTutorialButton.setOnClickListener {
            Toast.makeText(this, "Skip Tutorial", Toast.LENGTH_LONG).show()
        }
        tutorialCloseButton.setOnClickListener {
            Toast.makeText(this, "Close Tutorial", Toast.LENGTH_LONG).show()
        }
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
                getStartedButton.setText(R.string.next_tutorial_button)
            }
            1 -> {
                firstDotImageView.setImageResource(R.drawable.disable_position_icon)
                secondDotImageView.setImageResource(R.drawable.current_position_icon)
                thirdDotImageView.setImageResource(R.drawable.disable_position_icon)
                getStartedButton.setText(R.string.next_tutorial_button)
            }
            2 -> {
                firstDotImageView.setImageResource(R.drawable.disable_position_icon)
                secondDotImageView.setImageResource(R.drawable.disable_position_icon)
                thirdDotImageView.setImageResource(R.drawable.current_position_icon)
                getStartedButton.setText(R.string.start_tutorial_button)
            }
        }
    }

}
