package com.accenture.socialdistancing.ui.news

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.accenture.socialdistancing.R
import com.accenture.socialdistancing.model.TrustedSource
import com.squareup.picasso.Picasso

class FeedAdapter(private val context: Context?, private val feed: List<TrustedSource>) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {
    class FeedViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.feed_card, parent, false) as CardView
        return FeedViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val tvTitle = holder.cardView.findViewById(R.id.tvFeedTitle) as TextView
        val tvDescription = holder.cardView.findViewById(R.id.tvFeedDescription) as TextView
        val tvLink = holder.cardView.findViewById(R.id.tvFeedLink) as TextView
        val image = holder.cardView.findViewById(R.id.imgFeedCard) as ImageView

        Picasso.get().load(feed[position].photoLink).fit().into(image)
        tvTitle.text = feed[position].title
        tvDescription.text = feed[position].description
        tvLink.text = feed[position].link

        holder.cardView.setOnClickListener {
            val url = Uri.parse(feed[position].link)
            if (context != null) {
                startActivity(context, Intent(Intent.ACTION_VIEW, url), null)
            }
        }
    }

    override fun getItemCount() = feed.size
}