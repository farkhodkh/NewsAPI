package ru.farhodhaknazarov.newsapi.presenters

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import ru.farhodhaknazarov.newsapi.Constants
import ru.farhodhaknazarov.newsapi.MainActivity
import ru.farhodhaknazarov.newsapi.R
import ru.farhodhaknazarov.newsapi.adapters.NewsCollectionPagerAdapter
import ru.farhodhaknazarov.newsapi.fragments.*
import ru.farhodhaknazarov.newsapi.repo.entities.Channel

class MainActivityPresenter(var activity: MainActivity) {
    fun prepareView() {
        var pager = activity.findViewById<ViewPager>(R.id.pager)
        var tabLayout = activity.findViewById<TabLayout>(R.id.tab_layout)
        var adapter = NewsCollectionPagerAdapter(activity.supportFragmentManager)

        preparePagerAdapter(adapter)
        pager.adapter = adapter
    }

    fun preparePagerAdapter(adapter: NewsCollectionPagerAdapter) {

        MainActivityPresenter.updateFragmentsMap("Channels", ChannelsListFragment())
        MainActivityPresenter.updateFragmentsMap("Favorites", FavoriteChannelsFragment())
        MainActivityPresenter.updateFragmentsMap("Channels", ChannelsListFragment())
        MainActivityPresenter.updateFragmentsMap("Top", NewsListFragment("Top"))
        MainActivityPresenter.updateFragmentsMap("Everything", NewsListFragment("Everything"))

        adapter.fragments.add(MainActivityPresenter.getNewsApiFragment("Channels"))
        adapter.fragments.add(MainActivityPresenter.getNewsApiFragment("Favorites"))
        adapter.fragments.add(MainActivityPresenter.getNewsApiFragment("Top"))
        adapter.fragments.add(MainActivityPresenter.getNewsApiFragment("Everything"))
        adapter.titels.add("Channels")
        adapter.titels.add("Favorites")
        adapter.titels.add("Top")
        adapter.titels.add("Everything")
    }

    companion object{

        private var context: Context? = null
        private var preferences:SharedPreferences? = null
        private var fragmentsMap: MutableMap<String, AbstractNewsApiFragments> = mutableMapOf()

        fun updateFragmentsMap(fragmentName: String, fragment: AbstractNewsApiFragments){
            fragmentsMap.put(fragmentName, fragment)
        }

        fun getNewsApiFragment(fragmentName: String): AbstractNewsApiFragments? {
            return fragmentsMap.get(fragmentName)
        }

        fun setContext(con: Context?) {
            context = con
            preferences = PreferenceManager.getDefaultSharedPreferences(context)
        }

        fun getfavoriteChannelsList(): List<String>{
            var favoriteChannels: String = preferences?.getString(Constants.localStorageName, "")!!

            return favoriteChannels.split(",").map { it.trim() }
        }

        fun updateChannelsList(channel: Channel, fragment: AbstractNewsApiFragments) {
            var resultList: List<String> = getfavoriteChannelsList()
            val favFragment: FavoriteChannelsFragment? = MainActivityPresenter.getNewsApiFragment("Favorites") as FavoriteChannelsFragment?
            val channelsDataset = favFragment?.viewAdapter?.channelsDataset
            var newResultList: List<String> = listOf()

            var message = ""

            if (resultList.firstOrNull { it == channel.id } == null) {
                newResultList = resultList.plus(channel.id)
                message = "Channel ${channel.name} added to favorites"
                channelsDataset?.add(channel)
            } else {
                newResultList = resultList.filterNot { it == channel.id }
                message = "Channel ${channel.name} removed from favorites"
                channelsDataset?.remove(channel)
            }

            favFragment?.viewAdapter?.notifyDataSetChanged()

            val prefEditor = preferences?.edit()

            prefEditor?.putString(
                Constants.localStorageName,
                newResultList.joinListWhitSeparator()
            )

            prefEditor?.commit()
            fragment.showToast(message)

            favFragment?.viewAdapter?.channelsDataset
        }

        fun List<String>.joinListWhitSeparator(): String {
            var result = ""
            this.map { result = if (result == "") it else "$result,$it" }
            return result
        }

        fun setCurrentPage(pageNumber: Int){
            val activity = MainActivity.getInstance()
            activity.pager.currentItem = pageNumber
        }
    }
}