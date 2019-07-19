package ru.farhodhaknazarov.newsapi.repo.entities

class Article(
    var source: Source,
    var title: String,
    var author: String,
    var description: String,
    var url: String,
    var urlToImage: String,
    var publishedAt: String,
    var content: String
){
    constructor(title: String, author: String) : this(Source("0", "source"), title, author, "", "", "", "", "")
}

class Source(var id: String, var name: String)