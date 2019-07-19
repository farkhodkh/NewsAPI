package ru.farhodhaknazarov.newsapi.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.farhodhaknazarov.newsapi.R
import ru.farhodhaknazarov.newsapi.adapters.NewsRecyclerViewAdapter
import ru.farhodhaknazarov.newsapi.presenters.NewsListFragmentPresenter

class NewsListFragment(var resource: String) : AbstractNewsApiFragments() {

    lateinit var recyclerView: RecyclerView
    var viewAdapter: NewsRecyclerViewAdapter? = null
    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var searchTextView: EditText
    lateinit var linearLayout: LinearLayout
    lateinit var btnSearch: Button
    lateinit var progressBar: ProgressBar

    lateinit var presenter: NewsListFragmentPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_news_list, container, false)

        btnSearch = view.findViewById(R.id.btnSearch)
        searchTextView = view.findViewById(R.id.searchText)
        progressBar = view.findViewById(R.id.progressBar)
        linearLayout = view.findViewById(R.id.linearLayout)
        if(resource.equals("Everything"))
            linearLayout.visibility = View.VISIBLE

        progressBar.visibility = View.INVISIBLE
        presenter = NewsListFragmentPresenter(this)
        viewManager = LinearLayoutManager(context)

        recyclerView = view.findViewById<RecyclerView>(R.id.chanals_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
        }

        presenter.prepareRecyclerViewAdapter(recyclerView, resource)

        btnSearch.setOnClickListener(View.OnClickListener {
            progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.INVISIBLE
            val search = searchTextView.text.toString()
            presenter.updateArticlesByQuery(search, resource)
        })

        return view
    }

    override fun onDestroyView() {
        presenter.onDestroyViewFragment()
        super.onDestroyView()
    }
}
