package ru.farhodhaknazarov.newsapi.repo

import ru.farhodhaknazarov.newsapi.repo.entities.Article

class NewsApiTestData {
    companion object {
        fun getNews(): ArrayList<Article> {
            var articleArrayList: ArrayList<Article> = arrayListOf()

//            articleArrayList.add(Article("Some more title", "Author 2"))
//            articleArrayList.add(Article("Some title", "Author 1"))
//            articleArrayList.add(Article("Some title 3", "Author 3"))

            return articleArrayList
        }
    }
}