package ru.farhodhaknazarov.newsapi.presenters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import ru.farhodhaknazarov.newsapi.adapters.NewsRecyclerViewAdapter
import ru.farhodhaknazarov.newsapi.fragments.NewsListFragment
import ru.farhodhaknazarov.newsapi.repo.NewsApiService
import ru.farhodhaknazarov.newsapi.repo.entities.Article
import ru.farhodhaknazarov.newsapi.repo.entities.Articles
import ru.farhodhaknazarov.newsapi.repo.entities.Channel
import java.util.*
import kotlin.collections.ArrayList

class NewsListFragmentPresenter(var fragment: NewsListFragment) {

    var disposable: Disposable? = null

    fun prepareRecyclerViewAdapter(recyclerView: RecyclerView, resource: String) {

        var observable = when(resource){
            "Top" -> NewsApiService.instance.getArticlesForRu<ArrayList<Article>>()
            "Everything" -> NewsApiService.instance.getArticlesByLanguage<ArrayList<Article>>()
            else -> null
        }

        disposable = observable?.subscribe { art ->
            fragment.viewAdapter = NewsRecyclerViewAdapter(art.articles as ArrayList<Article>, fragment.context)
            fragment.recyclerView.adapter = fragment.viewAdapter
            fragment.viewAdapter?.notifyDataSetChanged()
        }
    }

    fun updateArticlesByQuery(query: String, resource: String) {

        if (query.length <= 3) {
            fragment.recyclerView.visibility = View.VISIBLE
            fragment.progressBar.visibility = View.INVISIBLE
            return
        }

        var observable = when(resource){
            "Top" -> NewsApiService.instance.getArticlesByQuery<ArrayList<Article>>(query)
            "Everything" -> NewsApiService.instance.getArticlesByQueryEveruthing<ArrayList<Article>>(query)
            else -> null
        }
//        var observable = NewsApiService.instance.getArticlesByQuery<ArrayList<Article>>(query)

        disposable = observable?.subscribe { art ->
            val result = art.articles as ArrayList<Article>

            fragment.viewAdapter?.articleDataset!!.clear()
            fragment.viewAdapter?.articleDataset!!.addAll(result)
            fragment.viewAdapter?.notifyDataSetChanged()

            fragment.recyclerView.visibility = View.VISIBLE
            fragment.progressBar.visibility = View.INVISIBLE
        }
    }

    fun getNewsForChannels(channel: Channel) {
        var observable = NewsApiService.instance.getNewsForChannels<ArrayList<Article>>(channel.id)

        disposable = observable?.subscribe { art ->
            if(art.articles==null)
                return@subscribe

            fragment.viewAdapter = NewsRecyclerViewAdapter(
                art.articles as ArrayList<Article>, fragment.context
            )
            fragment.recyclerView.adapter = fragment.viewAdapter
            fragment.viewAdapter?.notifyDataSetChanged()
            MainActivityPresenter.setCurrentPage(2)
        }
    }

    fun onDestroyViewFragment() {
        disposable?.dispose()
    }

}