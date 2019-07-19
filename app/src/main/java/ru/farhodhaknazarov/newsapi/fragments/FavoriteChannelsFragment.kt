package ru.farhodhaknazarov.newsapi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.farhodhaknazarov.newsapi.R
import ru.farhodhaknazarov.newsapi.adapters.FavoriteChannelsRecyclerViewAdapter
import ru.farhodhaknazarov.newsapi.presenters.FavoriteChannelsFragmentPresenter

class FavoriteChannelsFragment : AbstractNewsApiFragments() {
    lateinit var recyclerView: RecyclerView
    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var presenter: FavoriteChannelsFragmentPresenter
    var viewAdapter: FavoriteChannelsRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_favorite_channels, container, false)
        presenter = FavoriteChannelsFragmentPresenter(this)
        viewManager = LinearLayoutManager(context)

        recyclerView = view.findViewById<RecyclerView>(R.id.favorite_chanels_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
        }

        presenter.prepareRecyclerViewAdapter(recyclerView)
        return view
    }

    override fun onDestroyView() {
        presenter.onDestroyView()
        super.onDestroyView()
    }
}
