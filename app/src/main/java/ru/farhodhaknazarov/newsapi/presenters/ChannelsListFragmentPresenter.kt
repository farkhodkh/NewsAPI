package ru.farhodhaknazarov.newsapi.presenters

import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.Disposable
import ru.farhodhaknazarov.newsapi.adapters.ChannelsRecyclerViewAdapter
import ru.farhodhaknazarov.newsapi.fragments.ChannelsListFragment
import ru.farhodhaknazarov.newsapi.repo.NewsApiService
import ru.farhodhaknazarov.newsapi.repo.entities.Channel
import ru.farhodhaknazarov.newsapi.repo.entities.ChannelsList

class ChannelsListFragmentPresenter(var fragment: ChannelsListFragment) {
    var disposable: Disposable? = null

    fun prepareRecyclerViewAdapter(recyclerView: RecyclerView?) {

        var observable = NewsApiService.instance.getChannels<ArrayList<ChannelsList>>()

        disposable = observable?.subscribe { chanel ->
            fragment.viewAdapter =
                ChannelsRecyclerViewAdapter(chanel.sources, fragment.context, this)
            fragment.recyclerView.adapter = fragment.viewAdapter
            fragment.viewAdapter?.notifyDataSetChanged()
        }
    }

    fun updateChannelsList(channel: Channel) {
        MainActivityPresenter.updateChannelsList(channel, fragment)
    }

    fun onDestroyView() {
        disposable?.dispose()
    }
}