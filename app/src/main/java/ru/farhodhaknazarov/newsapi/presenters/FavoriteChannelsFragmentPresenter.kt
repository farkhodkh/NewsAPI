package ru.farhodhaknazarov.newsapi.presenters

import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.Disposable
import ru.farhodhaknazarov.newsapi.adapters.FavoriteChannelsRecyclerViewAdapter
import ru.farhodhaknazarov.newsapi.fragments.FavoriteChannelsFragment
import ru.farhodhaknazarov.newsapi.fragments.NewsListFragment
import ru.farhodhaknazarov.newsapi.repo.NewsApiService
import ru.farhodhaknazarov.newsapi.repo.entities.Channel
import ru.farhodhaknazarov.newsapi.repo.entities.ChannelsList

class FavoriteChannelsFragmentPresenter(var fragment: FavoriteChannelsFragment) {
    var disposable: Disposable? = null

    fun prepareRecyclerViewAdapter(recyclerView: RecyclerView?) {
        var observable = NewsApiService.instance.getChannels<ArrayList<ChannelsList>>()

        MainActivityPresenter.setContext(fragment.context)

        val favoriteList = MainActivityPresenter.getfavoriteChannelsList()

        disposable = observable?.subscribe { chanel ->

            var newList: ArrayList<Channel> = chanel.sources.filter { channel -> favoriteList.firstOrNull { it == channel.id } != null } as ArrayList<Channel>

            fragment.viewAdapter =
                FavoriteChannelsRecyclerViewAdapter(newList, fragment.context, this)
            fragment.recyclerView.adapter = fragment.viewAdapter
            fragment.viewAdapter?.notifyDataSetChanged()
        }
    }

    fun onDestroyView() {
        disposable?.dispose()
    }

    fun updateChannalsList(channel: Channel) {
        MainActivityPresenter.updateChannelsList(channel, fragment)
        fragment.viewAdapter?.notifyDataSetChanged()
    }

    fun openSourceNews(channel: Channel) {
        val topNewsFragment: NewsListFragment = MainActivityPresenter.getNewsApiFragment("Top") as NewsListFragment
        topNewsFragment.presenter.getNewsForChannels(channel)
    }
}