package ru.farhodhaknazarov.newsapi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.farhodhaknazarov.newsapi.R
import ru.farhodhaknazarov.newsapi.presenters.ChannelsListFragmentPresenter
import ru.farhodhaknazarov.newsapi.repo.entities.Channel

class ChannelsRecyclerViewAdapter(var channelsDataset: ArrayList<Channel>, context: Context?, var presenter: ChannelsListFragmentPresenter) :
    RecyclerView.Adapter<ChannelsRecyclerViewAdapter.ChannelsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelsViewHolder {
        val channelsItemView =
            LayoutInflater.from(parent.context).inflate(R.layout.channels_item_view, parent, false) as LinearLayout

        return ChannelsViewHolder(channelsItemView)

    }

    override fun getItemCount(): Int = channelsDataset.size

    override fun onBindViewHolder(holder: ChannelsViewHolder, position: Int) {
        val channelName = holder.newsItemView.findViewById<TextView>(R.id.channel_name)
        val channelDescription = holder.newsItemView.findViewById<TextView>(R.id.channel_description)
        val channelCategory = holder.newsItemView.findViewById<TextView>(R.id.channel_category)
        val channelLanguage = holder.newsItemView.findViewById<TextView>(R.id.channel_language)
        val btnJoin = holder.newsItemView.findViewById<TextView>(R.id.btn_favorites)

        val btnShowNews = holder.newsItemView.findViewById<TextView>(R.id.btnShowNews)
        btnShowNews.visibility = View.GONE

        val channel = channelsDataset[position]
        btnJoin.text = "Join channel"
        btnJoin.setOnClickListener(View.OnClickListener {
            presenter.updateChannelsList(channel)
        })

        channelName.text = channel.name
        channelDescription.text = channel.description
        channelCategory.text = channel.category
        channelLanguage.text = channel.language
    }

    class ChannelsViewHolder(val newsItemView: LinearLayout) : RecyclerView.ViewHolder(newsItemView)
}