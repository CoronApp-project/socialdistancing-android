package com.accenture.socialdistancing.ui.trustedsources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.accenture.socialdistancing.R
import com.accenture.socialdistancing.model.TrustedSource
import com.google.gson.Gson


class TrustedSourcesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_trusted_sources, container, false)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = FeedAdapter(context, getParsedFeed())

        recyclerView = root.findViewById<RecyclerView>(R.id.rvFeed).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return root
    }


    private fun getParsedFeed() : List<TrustedSource> {
        val objectArrayString: String = context!!.resources.openRawResource(R.raw.trusted_sources).bufferedReader().use { it.readText() }
        return Gson().fromJson(objectArrayString, Array<TrustedSource>::class.java).toList()
    }
}