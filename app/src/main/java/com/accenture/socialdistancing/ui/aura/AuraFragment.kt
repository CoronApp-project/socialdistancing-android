package com.accenture.socialdistancing.ui.aura

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.accenture.socialdistancing.R

class AuraFragment : Fragment() {

    private lateinit var auraViewModel: AuraViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auraViewModel =
            ViewModelProviders.of(this).get(AuraViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_aura, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        auraViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}