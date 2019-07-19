package ru.farhodhaknazarov.newsapi.adapters

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ru.farhodhaknazarov.newsapi.fragments.AbstractNewsApiFragments

class NewsCollectionPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    var fragments: ArrayList<AbstractNewsApiFragments?> = arrayListOf()
    var titels: ArrayList<String> = arrayListOf()

    override fun getItem(position: Int): AbstractNewsApiFragments? {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titels[position]
    }
}