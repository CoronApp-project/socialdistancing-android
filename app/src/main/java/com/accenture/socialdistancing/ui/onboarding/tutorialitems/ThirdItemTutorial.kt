package com.accenture.socialdistancing.ui.onboarding.tutorialitems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.accenture.socialdistancing.R

class ThirdItemTutorial : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.layout_third_item_tutorial, container, false)
    }
}