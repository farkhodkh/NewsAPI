package ru.farhodhaknazarov.newsapi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.farhodhaknazarov.newsapi.R
import ru.farhodhaknazarov.newsapi.adapters.ChannelsRecyclerViewAdapter
import ru.farhodhaknazarov.newsapi.presenters.ChannelsListFragmentPresenter

class ChannelsListFragment : AbstractNewsApiFragments() {
    lateinit var recyclerView: RecyclerView
    var viewAdapter: ChannelsRecyclerViewAdapter? = null
    lateinit var viewManager: RecyclerView.LayoutManager

    lateinit var presenter: ChannelsListFragmentPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_channels_list, container, false)
        presenter = ChannelsListFragmentPresenter(this)
        viewManager = LinearLayoutManager(context)

        recyclerView = view.findViewById<RecyclerView>(R.id.chanals_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
        }

        presenter.prepareRecyclerViewAdapter(recyclerView)

        return view
    }

//    fun showToast(message: String) {
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//    }

    override fun onDestroyView() {
        presenter.onDestroyView()
        super.onDestroyView()
    }
}
