package ru.farhodhaknazarov.newsapi.repo.entities

class Articles(
    var status: String,
    var totalResults: Int,
    var articles: List<Article>
)