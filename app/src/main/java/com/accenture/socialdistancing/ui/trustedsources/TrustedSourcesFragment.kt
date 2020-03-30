package com.accenture.socialdistancing.ui.trustedsources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.accenture.socialdistancing.R

class TrustedSourcesFragment : Fragment() {

    private lateinit var trustedSourcesViewModel: TrustedSourcesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        trustedSourcesViewModel =
            ViewModelProviders.of(this).get(TrustedSourcesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_trusted_sources, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        trustedSourcesViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}