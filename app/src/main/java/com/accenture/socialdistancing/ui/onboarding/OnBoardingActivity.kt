package com.accenture.socialdistancing.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.accenture.socialdistancing.MainActivity
import com.accenture.socialdistancing.R
import com.accenture.socialdistancing.ui.onboarding.tutorialitems.FirstItemTutorial
import com.accenture.socialdistancing.ui.onboarding.tutorialitems.SecondItemTutorial
import com.accenture.socialdistancing.ui.onboarding.tutorialitems.ThirdItemTutorial
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {

    companion object {
        private const val PREFERENCES_NAME = "preferences"
        private const val FIRST_TIME_PREFERENCE = "first_time"
    }

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
                saveTutorialCheckedPreference()
                goToMainScreen()
            }
        }
        skipTutorialButton.setOnClickListener {
            goToMainScreen()
        }
        tutorialCloseButton.setOnClickListener {
            goToMainScreen()
        }
    }

    private fun saveTutorialCheckedPreference() {
        val sharedPreference = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        sharedPreference.edit().putBoolean(FIRST_TIME_PREFERENCE, false).apply()
    }

    private fun goToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun addPagerFragments() {
        onBoardingAdapter.addFragments(FirstItemTutorial())
        onBoardingAdapter.addFragments(SecondItemTutorial())
        onBoardingAdapter.addFragments(ThirdItemTutorial())
    }

    override fun onBackPressed() {
        val currentPosition = tutorialViewPager.currentItem
        if (tutorialViewPager.currentItem == 0) {
            finish()
        } else {
            tutorialViewPager.currentItem = currentPosition - 1
        }
    }

    private fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                firstDotImageView.setImageResource(R.drawable.current_position_icon)
                secondDotImageView.setImageResource(R.drawable.disable_position_icon)
                thirdDotImageView.setImageResource(R.drawable.disable_position_icon)
                getStartedButton.text = getString(R.string.next_tutorial_button)
                skipTutorialButton.visibility = View.VISIBLE
            }
            1 -> {
                firstDotImageView.setImageResource(R.drawable.disable_position_icon)
                secondDotImageView.setImageResource(R.drawable.current_position_icon)
                thirdDotImageView.setImageResource(R.drawable.disable_position_icon)
                getStartedButton.text = getString(R.string.next_tutorial_button)
                skipTutorialButton.visibility = View.VISIBLE
            }
            2 -> {
                firstDotImageView.setImageResource(R.drawable.disable_position_icon)
                secondDotImageView.setImageResource(R.drawable.disable_position_icon)
                thirdDotImageView.setImageResource(R.drawable.current_position_icon)
                getStartedButton.text = getString(R.string.start_tutorial_button)
                skipTutorialButton.visibility = View.GONE
            }
        }
    }
}